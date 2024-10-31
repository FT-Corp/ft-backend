package com.spring.ftbackend.orders.repository;

import com.spring.ftbackend.orders.domain.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Orders, Long> {

}
