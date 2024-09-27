package com.spring.ftbackend.Subscription.dto.request;

import lombok.Getter;

@Getter
public class UserSubscriptionSaveRequestDto {
    private long userId;
    private long subscriptionId;
    private String paymentStatus;
}
