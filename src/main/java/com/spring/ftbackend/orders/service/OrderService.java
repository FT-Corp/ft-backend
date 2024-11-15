package com.spring.ftbackend.orders.service;

import com.spring.ftbackend.book.repository.BookRepository;
import com.spring.ftbackend.book.domain.Book;
import com.spring.ftbackend.orders.domain.SubscriptionOrders;
import com.spring.ftbackend.orders.dto.SubscriptionOrderSaveRequestDto;
import com.spring.ftbackend.orders.repository.SubscriptionOrderRepository;
import com.spring.ftbackend.subscription.domain.Subscription;
import com.spring.ftbackend.subscription.domain.UserSubscription;
import com.spring.ftbackend.subscription.dto.request.UserSubscriptionSaveRequestDto;
import com.spring.ftbackend.subscription.repository.SubscriptionRepository;
import com.spring.ftbackend.subscription.repository.UserSubscriptionRepository;
import com.spring.ftbackend.subscription.service.UserSubscriptionService;
import com.spring.ftbackend.user.repository.UserRepository;
import com.spring.ftbackend.user.domain.User;
import com.spring.ftbackend.orders.domain.Orders;
import com.spring.ftbackend.orders.dto.OrderSaveRequestDto;
import com.spring.ftbackend.orders.repository.OrderRepository;
import com.spring.ftbackend.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrderService {
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final SubscriptionOrderRepository subscriptionOrderRepository;
    private final UserSubscriptionRepository userSubscriptionRepository;
    private final UserSubscriptionService userSubscriptionService;

    //외부노출 가능한 OrderUid 필요
    public Long saveOrder(OrderSaveRequestDto orderSaveRequestDto) {
        User user = userRepository.findById(orderSaveRequestDto.getUserId())
                .orElseThrow(() -> new IllegalStateException("User Not Found"));

        Book book = bookRepository.findById(orderSaveRequestDto.getBookId())
                .orElseThrow(() -> new IllegalStateException("Book Not Found"));

        Orders orders = Orders.from(book,user
                , orderSaveRequestDto.getTotalAmount()
                , orderSaveRequestDto.getDiscountAmount()
                , orderSaveRequestDto.isFreeContent());

        orderRepository.save(orders);
        return orders.getId();
    }

    public Long saveSubscriptionOrder(SubscriptionOrderSaveRequestDto request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalStateException("User Not Found"));
        Subscription subscription = subscriptionRepository.findById(request.getSubscriptionId())
                .orElseThrow(() -> new IllegalStateException("Subscription Not Found"));

        SubscriptionOrders subscriptionOrders = SubscriptionOrders.from(subscription,user,request.getTotalAmount());

        subscriptionOrderRepository.save(subscriptionOrders);
        //검증 로직 미구현
        return subscriptionOrders.getId();
    }

    public Integer getOrderTotalAmount(Long id) {
        Orders orders = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User Not Found"));
        return orders.getTotalAmount();
    }

    public Integer getSubscriptionTotalAmount(Long id) {
        SubscriptionOrders orders = subscriptionOrderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Subscription Not Found"));
        return orders.getTotalAmount();
    }



}
