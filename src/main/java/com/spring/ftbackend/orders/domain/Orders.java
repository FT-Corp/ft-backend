package com.spring.ftbackend.orders.domain;

import com.spring.ftbackend.book.domain.Book;
import com.spring.ftbackend.common.entity.BaseEntity;
import com.spring.ftbackend.login.domain.Users;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Orders extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//    private String orderId; 주문정보 조회용 id

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;


    private Integer totalAmount;

    private Integer discountAmount;

    private boolean isFreeContent;

    //payment enum 따로 빼서 공통처리 (Subscription payment 함께)
    @Enumerated(EnumType.STRING)
    private OrderPaymentStatus orderPaymentStatus;

    public static Orders from(Book book1, Users user1, Integer totalAmount, Integer discountAmount, boolean isFreeContent) {
        return Orders.builder()
                .totalAmount(totalAmount)
                .discountAmount(discountAmount)
                .isFreeContent(isFreeContent)
                .book(book1)
                .user(user1)
                .orderPaymentStatus(OrderPaymentStatus.PENDING)
                .build();
    }

    public enum OrderPaymentStatus {
        PENDING("미결제"),
        COMPLETED("결제완료"),
        ;
        private final String status;

        OrderPaymentStatus(String status) {
            this.status = status;
        }

        public String getStatus() {
            return status;
        }

        public static OrderPaymentStatus from(String status) {
            return Arrays.stream(values())
                    .filter(paymentStatus1 -> paymentStatus1.getStatus().equals(status))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Unknown status " + status));

        }
    }

    public void completeOrder(){
        this.orderPaymentStatus = OrderPaymentStatus.COMPLETED;
    }
}
