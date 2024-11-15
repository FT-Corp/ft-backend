package com.spring.ftbackend.orders.dto;

import lombok.Getter;

@Getter
public class SubscriptionOrderSaveRequestDto {
    private Integer totalAmount;
    private Long userId;
    private Long subscriptionId;
}
