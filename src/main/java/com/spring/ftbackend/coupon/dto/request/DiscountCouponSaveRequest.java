package com.spring.ftbackend.coupon.dto.request;

import lombok.NonNull;

import java.time.LocalDateTime;

public record DiscountCouponSaveRequest(
        String context,
        Integer discountRate,
        Integer discountAmount,
        Integer duration
){}
