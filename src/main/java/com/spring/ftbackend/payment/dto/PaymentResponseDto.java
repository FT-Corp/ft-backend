package com.spring.ftbackend.payment.dto;

import lombok.Getter;

@Getter
public class PaymentResponseDto {
    private String status;
    private Amount amount;
    private String paymentMethod;

    @Getter
    public static class Amount{
        private int total;
    }
}
