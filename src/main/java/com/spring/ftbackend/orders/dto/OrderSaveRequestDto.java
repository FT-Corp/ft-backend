package com.spring.ftbackend.orders.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@NoArgsConstructor
@Getter
public class OrderSaveRequestDto {
    private Integer totalAmount;
    private Integer discountAmount;
    private boolean isFreeContent;
    private List<Long> bookIds;
    private Long userId;
}
