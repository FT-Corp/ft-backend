package com.spring.ftbackend.payment.service;

import com.spring.ftbackend.orders.domain.Orders;
import com.spring.ftbackend.orders.domain.SubscriptionOrders;
import com.spring.ftbackend.orders.repository.SubscriptionOrderRepository;
import com.spring.ftbackend.orders.service.OrderService;
import com.spring.ftbackend.payment.domain.Payment;
import com.spring.ftbackend.payment.domain.SubscriptionPayment;
import com.spring.ftbackend.payment.dto.PaymentResponseDto;
import com.spring.ftbackend.payment.dto.PaymentSaveRequestDto;
import com.spring.ftbackend.payment.dto.SubscriptionPaymentSaveRequest;
import com.spring.ftbackend.payment.repository.SubscriptionPaymentRepository;
import com.spring.ftbackend.subscription.dto.request.UserSubscriptionSaveRequestDto;
import com.spring.ftbackend.subscription.service.UserSubscriptionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Slf4j
@Service
public class SubscriptionPaymentService {

    private final WebClient webClient;
    private final OrderService orderService;
    private final String PORTONE_API_SECRET = "jYAkxKT5Rs1rFE4xmFANSRMMsYtNgeRTSCEk2GKdIL6PIVPXbmMqDcihkHDY2xn6gNzqcpG9h4mjGk3h";
    private final SubscriptionPaymentRepository subscriptionPaymentRepository;
    private final SubscriptionOrderRepository subscriptionOrderRepository;
    private final UserSubscriptionService userSubscriptionService;

    public SubscriptionPaymentService(WebClient.Builder webClientBuilder, OrderService orderService, SubscriptionPaymentRepository subscriptionPaymentRepository, SubscriptionOrderRepository subscriptionOrderRepository, UserSubscriptionService userSubscriptionService) {
        this.webClient = webClientBuilder.baseUrl("http://api.portone.io").build();
        this.orderService = orderService;
        this.subscriptionPaymentRepository = subscriptionPaymentRepository;
        this.subscriptionOrderRepository = subscriptionOrderRepository;
        this.userSubscriptionService = userSubscriptionService;
    }

    public Mono<String> processPayment(SubscriptionPaymentSaveRequest paymentSaveRequestDto) throws UnsupportedEncodingException {
        // Encode the transaction ID to be safe for URLs
        String encodedTransactionId = URLEncoder.encode(paymentSaveRequestDto.getTransactionId(), StandardCharsets.UTF_8.toString());

        return webClient.get()
                .uri("/payments/" + encodedTransactionId)
                .header(HttpHeaders.AUTHORIZATION, "PortOne " + PORTONE_API_SECRET)
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse ->
                        Mono.error(new RuntimeException("Payment fetch failed: " + clientResponse.statusCode()))
                )
                .bodyToMono(PaymentResponseDto.class)
                .publishOn(Schedulers.boundedElastic())
                .flatMap(paymentResponse -> {

                    //order조회(payment 비즈니스 로직 -> payment실패시 order로 재결제 가능해야하므로)
                    Long orderId = paymentSaveRequestDto.getOrderId();
                    SubscriptionOrders orders = subscriptionOrderRepository.findById(orderId).orElseThrow(RuntimeException::new);

                    // Fetch the total amount from the order service
                    Integer totalAmount = orderService.getSubscriptionTotalAmount(orderId);
                    // Compare the payment amount with the order total
                    if (totalAmount.equals(paymentResponse.getAmount().getTotal())) {
                        switch (paymentResponse.getStatus()) {
                            case "VIRTUAL_ACCOUNT_ISSUED":
//                                return Mono.just("Payment successful");
                            case "PAID":
                                orders.completeOrder();
                                subscriptionOrderRepository.save(orders);
                                subscriptionPaymentRepository.save(SubscriptionPayment.from(paymentSaveRequestDto.getTransactionId(),totalAmount, orders));
                                return Mono.just("Payment successful");
                            default:
                                return Mono.error(new RuntimeException("Unexpected payment status: " + paymentResponse.getStatus()));
                        }
                    } else {
                        return Mono.error(new RuntimeException("Payment amount mismatch"));
                    }
                });
    }
}
