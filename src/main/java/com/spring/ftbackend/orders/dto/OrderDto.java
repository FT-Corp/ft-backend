package com.spring.ftbackend.orders.dto;

import com.spring.ftbackend.orders.domain.Orders;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class OrderDto {
    private Integer totalAmount;
    private Integer discountAmount;
    private boolean isFreeContent;
    private List<Long> bookId;
    private Long userId;

    public static OrderDto from(Orders orders) {
        return new OrderDto(
                orders.getTotalAmount(),
                orders.getDiscountAmount(),
                orders.isFreeContent(),
                orders.getBookIds(),
                orders.getUser().getUserId()
        );
    }
}
