package com.spring.ftbackend.payment.dto;

import lombok.Getter;

@Getter
public class PaymentSaveRequestDto {
    private String transactionId;
    private Integer paymentAmount;
    private String orderPaymentStatus;
    private String paymentType;
    private Long orderId;
}
