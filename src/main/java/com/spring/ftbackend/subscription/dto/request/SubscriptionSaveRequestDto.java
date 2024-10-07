package com.spring.ftbackend.subscription.dto.request;

import lombok.Getter;

@Getter
public class SubscriptionSaveRequestDto {
    private String subscriptionType;
    private Integer price;
    private Integer duration;
    private Integer free_content_limit;
}
