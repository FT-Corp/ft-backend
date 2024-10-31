package com.spring.ftbackend.payment.repository;

import com.spring.ftbackend.payment.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
