package com.spring.ftbackend.orders.domain;

import com.spring.ftbackend.book.domain.Book;
import com.spring.ftbackend.common.entity.BaseEntity;
import com.spring.ftbackend.subscription.domain.Subscription;
import com.spring.ftbackend.user.domain.User;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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
public class SubscriptionOrders extends BaseEntity {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "subscription_id", nullable = false)
    private Subscription subscription;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private Integer totalAmount;

    private Integer discountAmount;

    @Enumerated(EnumType.STRING)
    private SubscriptionOrderPaymentStatus subscriptionOrderPaymentStatus;

    public static SubscriptionOrders from(Subscription subscription, User user, Integer totalAmount) {
        return SubscriptionOrders.builder()
                .totalAmount(totalAmount)
                .subscription(subscription)
                .user(user)
                .subscriptionOrderPaymentStatus(SubscriptionOrderPaymentStatus.PENDING)
                .build();
    }

    public enum SubscriptionOrderPaymentStatus {
        PENDING("미결제"),
        COMPLETED("결제완료"),
        ;
        private final String status;

        SubscriptionOrderPaymentStatus(String status) { this.status = status;}

        public String getStatus() { return status; }

        public static SubscriptionOrderPaymentStatus from(String status) {
            return Arrays.stream(values())
                    .filter(paymentStatus1 -> paymentStatus1.getStatus().equals(status))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Invalid subscription order payment status: " + status));
        }
    }
    public void completeOrder() { this.subscriptionOrderPaymentStatus = SubscriptionOrderPaymentStatus.COMPLETED; }
}
