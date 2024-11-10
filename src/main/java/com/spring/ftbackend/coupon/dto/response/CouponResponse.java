package com.spring.ftbackend.coupon.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record CouponResponse(
        String context,
        Integer bookLimit,
        LocalDateTime issueDate,
        LocalDateTime expireDate,
        LocalDateTime redeemDate,
        Integer discountRate,
        Integer discountAmount
){}