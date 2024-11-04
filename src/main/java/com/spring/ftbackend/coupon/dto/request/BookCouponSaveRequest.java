package com.spring.ftbackend.coupon.dto.request;

import lombok.NonNull;

import java.time.LocalDateTime;

public record BookCouponSaveRequest(
        String context,
        @NonNull Integer bookLimit,
        Integer duration
){}
