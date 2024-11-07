package com.spring.ftbackend.credit.service;


import com.spring.ftbackend.credit.domain.Credit;
import com.spring.ftbackend.credit.domain.UserCredit;
import com.spring.ftbackend.credit.dto.request.UserCreditSaveRequest;
import com.spring.ftbackend.credit.dto.response.CreditResponse;
import com.spring.ftbackend.credit.repository.CreditRepository;
import com.spring.ftbackend.credit.repository.UserCreditRepository;
import com.spring.ftbackend.user.repository.UserRepository;
import com.spring.ftbackend.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserCreditService {
    private final UserCreditRepository userCreditRepository;
    private final UserRepository userRepository;
    private final CreditRepository creditRepository;

    public Integer getUserCreditAmount(Long userId){
        return userCreditRepository.findUserCreditAmountSumByUserId(userId);
    }

    public void registerUserCredit(UserCreditSaveRequest request) {
        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid user"));
        Credit credit = creditRepository.findById(request.creditId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid credit"));
        UserCredit userCredit = UserCredit.builder()
                .user(user)
                .credit(credit)
                .build();
        userCreditRepository.save(userCredit);
    }

    public List<CreditResponse> getUserCreditList(Long userId){
        return userCreditRepository.findUserCreditsByUserId(userId)
                .stream()
                .map(userCredit -> mapToCreditResponse(userCredit.getCredit()))
                .collect(Collectors.toList());
    }

    private CreditResponse mapToCreditResponse(Credit credit) {
        return CreditResponse.builder()
                .context(credit.getContext())
                .amount(credit.getAmount())
                .issueDate(credit.getIssueDate())
                .expireDate(credit.getExpireDate())
                .redeemDate(credit.getRedeemDate())
                .build();
    }
}
