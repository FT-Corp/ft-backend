package com.spring.ftbackend.payment.controller;

import com.spring.ftbackend.payment.domain.Payment;
import com.spring.ftbackend.payment.dto.PaymentSaveRequestDto;
import com.spring.ftbackend.payment.service.PaymentService;
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

    @PostMapping("complete")
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

}
