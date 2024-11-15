package com.spring.ftbackend.orders.controller;

import com.spring.ftbackend.orders.dto.SubscriptionOrderDto;
import com.spring.ftbackend.orders.dto.SubscriptionOrderSaveRequestDto;
import com.spring.ftbackend.user.repository.UserRepository;
import com.spring.ftbackend.orders.dto.OrderSaveRequestDto;
import com.spring.ftbackend.orders.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;
    private final UserRepository userRepository;

//    @GetMapping
//    public ResponseEntity<OrderDto> getAllOrders() {
//
//    }

    //response entity 도입, Long -> orderUid
    @Operation(summary = "책 주문 정보 생성")
    @PostMapping
    public ResponseEntity<Long> createOrder(@RequestBody OrderSaveRequestDto orderSaveRequestDto) {
        return ResponseEntity.ok()
                .body(orderService.saveOrder(orderSaveRequestDto));

    }

    @Operation(summary = "구독 주문 정보 생성")
    @PostMapping("/subscription")
    public ResponseEntity<Long> createSubscriptionOrder(@RequestBody SubscriptionOrderSaveRequestDto request) {
        return ResponseEntity.ok()
                .body(orderService.saveSubscriptionOrder(request));
    }

    //id get 조회방식 변경 필요
    @GetMapping("/{id}")
    public ResponseEntity<Integer> getOrderTotalAmountById(@PathVariable Long id) {
        return ResponseEntity.ok()
                .body(orderService.getOrderTotalAmount(id));
    }
}
