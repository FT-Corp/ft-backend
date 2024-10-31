package com.spring.ftbackend.orders.dto;

import com.spring.ftbackend.orders.domain.Orders;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderDto {
    private Integer totalAmount;
    private Integer discountAmount;
    private boolean isFreeContent;
    private Long bookId;
    private Long userId;

    public static OrderDto from(Orders orders) {
        return new OrderDto(
                orders.getTotalAmount(),
                orders.getDiscountAmount(),
                orders.isFreeContent(),
                orders.getBook().getBookId(),
                orders.getUser().getUserId()
        );
    }
}
