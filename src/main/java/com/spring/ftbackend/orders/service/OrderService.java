package com.spring.ftbackend.orders.service;

import com.spring.ftbackend.book.Repository.BookRepository;
import com.spring.ftbackend.book.domain.Book;
import com.spring.ftbackend.user.Repository.UserRepository;
import com.spring.ftbackend.user.domain.Users;
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

    //외부노출 가능한 OrderUid 필요
    public Long saveOrder(OrderSaveRequestDto orderSaveRequestDto) {
        Users user = userRepository.findById(orderSaveRequestDto.getUserId())
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
    public Integer getOrderTotalAmount(Long id) {
        Orders orders = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User Not Found"));

        return orders.getTotalAmount();
    }

}
