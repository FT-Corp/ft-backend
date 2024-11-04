package com.spring.ftbackend.coupon.repository;

import com.spring.ftbackend.coupon.domain.UserCoupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserCouponRepository extends JpaRepository<UserCoupon, Long> {
    @Query("SELECT COUNT(uc) FROM UserCoupon uc WHERE uc.user.userId = :userId")
    Integer findUserCouponCountByUserId(@Param("userId") Long userId);

    @Query("SELECT uc FROM UserCoupon uc WHERE uc.user.userId = :userId")
    List<UserCoupon> findUserCouponsByUserId(@Param("userId") Long userId);
}
