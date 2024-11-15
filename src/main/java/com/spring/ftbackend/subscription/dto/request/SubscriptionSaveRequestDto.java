package com.spring.ftbackend.subscription.dto.request;

import lombok.Getter;

import java.util.List;

@Getter
public class SubscriptionSaveRequestDto {
    private String subscriptionType;
    private Integer monthCost;
    private Integer yearCost;
    private Integer free_content_limit;
    private List<String> recommend;
}
