package com.spring.ftbackend.credit.service;

import com.spring.ftbackend.credit.domain.Credit;
import com.spring.ftbackend.credit.dto.request.CreditSaveRequest;
import com.spring.ftbackend.credit.dto.response.CreditResponse;
import com.spring.ftbackend.credit.repository.CreditRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreditService {
    private final CreditRepository creditRepository;

    public CreditResponse saveCredit(CreditSaveRequest request){
        Credit credit = Credit.builder()
                .context(request.context())
                .amount(request.amount())
                .build();
        //코드 가독성 및 유지보수성: save() twice -> 효율 측면: JpaAuditing 우회
        creditRepository.save(credit);
        credit.setExpireDate(request.duration());
        creditRepository.save(credit);

        return mapToCreditResponse(credit);
    }

    private CreditResponse mapToCreditResponse(Credit credit){
        return CreditResponse.builder()
                .amount(credit.getAmount())
                .context(credit.getContext())
                .issueDate(credit.getIssueDate())
                .expireDate(credit.getExpireDate())
                .redeemDate(credit.getRedeemDate())
                .build();
    }

}
