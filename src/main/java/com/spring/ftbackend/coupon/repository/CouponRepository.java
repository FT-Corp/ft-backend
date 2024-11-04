package com.spring.ftbackend.coupon.repository;

import com.spring.ftbackend.coupon.domain.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

}
