package com.spring.ftbackend.orders.dto;

import lombok.Getter;


@Getter
public class OrderSaveRequestDto {
    private Integer totalAmount;
    private Integer discountAmount;
    private boolean isFreeContent;
    private Long bookId;
    private Long userId;

}
