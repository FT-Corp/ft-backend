package com.spring.ftbackend.coupon.dto.request;


import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Builder
public record UserCouponSaveRequest(
        @NonNull Long userId,
        @NonNull Long couponId
) {}
