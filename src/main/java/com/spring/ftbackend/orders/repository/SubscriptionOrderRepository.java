package com.spring.ftbackend.orders.repository;

import com.spring.ftbackend.orders.domain.SubscriptionOrders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionOrderRepository extends JpaRepository<SubscriptionOrders, Long> {
}
