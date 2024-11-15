package com.spring.ftbackend.payment.controller;

import com.spring.ftbackend.payment.domain.Payment;
import com.spring.ftbackend.payment.dto.PaymentSaveRequestDto;
import com.spring.ftbackend.payment.dto.SubscriptionPaymentSaveRequest;
import com.spring.ftbackend.payment.service.PaymentService;
import com.spring.ftbackend.payment.service.SubscriptionPaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

@Slf4j
@RestController
@RequestMapping("/payment")
@Builder
@Getter
public class PaymentController {
    private final PaymentService paymentService;
    private final SubscriptionPaymentService subscriptionPaymentService;

    @Operation(summary = "결제 모듈 완료후 서버와 검증(책)")
    @PostMapping("/complete")
    public ResponseEntity<String> savePayment(@RequestBody PaymentSaveRequestDto paymentSaveRequestDto) {
        try {
            log.info(paymentSaveRequestDto.getPaymentType());
            log.info(paymentSaveRequestDto.getTransactionId());
            log.info(String.valueOf(paymentSaveRequestDto.getOrderId()));
            String paymentDetails = paymentService.processPayment(paymentSaveRequestDto).block();
            return ResponseEntity.ok(paymentDetails);
        } catch (UnsupportedEncodingException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid encoding for transaction ID");

        }
    }

    @Operation(summary = "결제 모듈 완료후 서버와 검증(구독)")
    @PostMapping("/complete/subscription")
    public ResponseEntity<String> savePaymentSubscription(@RequestBody SubscriptionPaymentSaveRequest request) {
        try {
            log.info(request.getPaymentType());
            log.info(request.getTransactionId());
            log.info(String.valueOf(request.getOrderId()));
            String paymentDetails = subscriptionPaymentService.processPayment(request).block();
            return ResponseEntity.ok(paymentDetails);
        } catch (UnsupportedEncodingException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid encoding for transaction ID");

        }
    }

}
