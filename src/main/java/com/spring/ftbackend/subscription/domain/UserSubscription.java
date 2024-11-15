package com.spring.ftbackend.subscription.domain;

import com.spring.ftbackend.common.entity.BaseEntity;
import com.spring.ftbackend.user.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Arrays;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class UserSubscription extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subscription_id", nullable = false)
    private Subscription subscription;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime endDate;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;



    //팩토리
    public static UserSubscription from(User user, Subscription subscription, LocalDateTime startDate, String paymentStatus, Integer duration){
        return UserSubscription.builder()
                .user(user)
                .subscription(subscription)
                .endDate(calculateEndDate(startDate, duration))
                .paymentStatus(PaymentStatus.from(paymentStatus))
                // .paymentStatus(PaymentStatus.PENDING) 필드 초기화로 해결
                .build();
    }

    public enum PaymentStatus{
        PENDING("미결제"),
        COMPLETED("결제완료"),
        ;
        private final String status;

        PaymentStatus(String status){this.status = status;}

        public String getStatus(){return status;}

        public static PaymentStatus from(String status){
            return Arrays.stream(values())
                    .filter(paymentStatus1 -> paymentStatus1.getStatus().equals(status))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Unknown status " + status));

        }
}
    public boolean validateForPayment() { return PaymentStatus.COMPLETED.equals(this.paymentStatus); }

    private static LocalDateTime calculateEndDate(LocalDateTime startDate, Integer duration) {
        return startDate.plusDays(duration);
    }

}
