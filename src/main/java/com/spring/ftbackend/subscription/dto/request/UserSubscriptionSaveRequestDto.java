package com.spring.ftbackend.subscription.dto.request;

import lombok.Getter;

@Getter
public class UserSubscriptionSaveRequestDto {
    private long userId;
    private long subscriptionId;
    private String paymentStatus;
}
