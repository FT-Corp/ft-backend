package com.spring.ftbackend.coupon.dto.request;

import lombok.NonNull;

import java.time.LocalDateTime;

public record DiscountCouponSaveRequest(
        String context,
        @NonNull LocalDateTime issueDate,
        @NonNull LocalDateTime expireDate,
        @NonNull Integer discountRate
){}
