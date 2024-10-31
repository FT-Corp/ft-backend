package com.spring.ftbackend.subscription.service;

import com.spring.ftbackend.subscription.domain.Subscription;
import com.spring.ftbackend.subscription.domain.UserSubscription;
import com.spring.ftbackend.subscription.dto.request.UserSubscriptionInfoRequestDto;
import com.spring.ftbackend.subscription.dto.request.UserSubscriptionSaveRequestDto;
import com.spring.ftbackend.subscription.dto.response.UserSubscriptionInfoResponseDto;
import com.spring.ftbackend.subscription.repository.SubscriptionRepository;
import com.spring.ftbackend.subscription.repository.UserSubscriptionRepository;
import com.spring.ftbackend.login.Repository.UserRepository;
import com.spring.ftbackend.login.domain.Users;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@RequestMapping
public class UserSubscriptionService {

    private final UserRepository userRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final UserSubscriptionRepository userSubscriptionRepository;

    public boolean saveUserSubscription(UserSubscriptionSaveRequestDto userSubscriptionSaveRequestDto) {
        log.info(String.valueOf(userSubscriptionSaveRequestDto.getUserId()));
        Users user = userRepository.findById(userSubscriptionSaveRequestDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Subscription subscription = subscriptionRepository.findById(userSubscriptionSaveRequestDto.getSubscriptionId())
                .orElseThrow(() -> new IllegalArgumentException("Subscription not found"));

        //이미 구독중인 plan 조회
        Optional<UserSubscription> userSubscriptionOpt = userSubscriptionRepository.findUserSubscriptionByUserId(userSubscriptionSaveRequestDto.getUserId());
        if (userSubscriptionOpt.isPresent()) {
            return false;
        }
        else{
            LocalDateTime startDate = LocalDateTime.now();

            UserSubscription userSubscription = UserSubscription.from(user, subscription, startDate, userSubscriptionSaveRequestDto.getPaymentStatus());
            userSubscriptionRepository.save(userSubscription);

            return true;
        }
    }

    public UserSubscriptionInfoResponseDto getUserSubscription(UserSubscriptionInfoRequestDto userSubscriptionInfoRequestDto){
        Long userId = userSubscriptionInfoRequestDto.getUserId();

        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        UserSubscription userSubscription = userSubscriptionRepository.findUserSubscriptionByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("User subscription not found"));

        log.info(String.valueOf(userSubscription.getPaymentStatus()));

        return UserSubscriptionInfoResponseDto.from(userSubscription);
    }
}
