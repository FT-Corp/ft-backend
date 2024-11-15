package com.spring.ftbackend.payment.repository;

import com.spring.ftbackend.payment.domain.SubscriptionPayment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionPaymentRepository extends JpaRepository<SubscriptionPayment, Long>{
}
