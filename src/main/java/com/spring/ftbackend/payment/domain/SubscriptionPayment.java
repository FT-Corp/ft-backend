package com.spring.ftbackend.payment.domain;

import com.spring.ftbackend.common.entity.BaseEntity;
import com.spring.ftbackend.orders.domain.Orders;
import com.spring.ftbackend.orders.domain.SubscriptionOrders;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.units.qual.A;

import java.util.Arrays;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class SubscriptionPayment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String transactionId;

    private Integer paymentAmount;

    @Enumerated(EnumType.STRING)
    private Payment.PaymentType paymentType;

    @ManyToOne
    @JoinColumn(name = "subscription_order_id", nullable = false)
    private SubscriptionOrders subscriptionOrders;

    public static SubscriptionPayment from(String transactionId, Integer paymentAmount, SubscriptionOrders orders) {
        return SubscriptionPayment.builder()
                .transactionId(transactionId)
                .paymentAmount(paymentAmount)
                .subscriptionOrders(orders)
                .build();
    }

    public enum PaymentType {
        PENDING("미결제"),
        COMPLETED("결제완료"),
        ;
        private final String status;

        PaymentType(String status) {
            this.status = status;
        }

        public String getStatus() {
            return status;
        }

        public static PaymentType from(String type) {
            return Arrays.stream(values())
                    .filter(paymentStatus1 -> paymentStatus1.getStatus().equals(type))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Unknown status " + type));

        }
    }



}
