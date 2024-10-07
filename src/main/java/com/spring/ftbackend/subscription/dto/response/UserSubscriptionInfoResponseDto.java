package com.spring.ftbackend.subscription.dto.response;

import com.spring.ftbackend.subscription.domain.UserSubscription;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class UserSubscriptionInfoResponseDto {
    private Long id;
    private Long userId;
    private String subscriptionId;
    private LocalDateTime endDate;
    private String paymentStatus;

    public static UserSubscriptionInfoResponseDto from(UserSubscription userSubscription) {
        return new UserSubscriptionInfoResponseDto(
                userSubscription.getId(),
                userSubscription.getUser().getUserId(),
                userSubscription.getSubscription().getId().toString(),
                userSubscription.getEndDate(),
                userSubscription.getPaymentStatus().getStatus()
        );
    }
}

