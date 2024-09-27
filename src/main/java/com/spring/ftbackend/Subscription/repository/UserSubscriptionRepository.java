package com.spring.ftbackend.Subscription.repository;

import com.spring.ftbackend.Subscription.domain.UserSubscription;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserSubscriptionRepository extends JpaRepository<UserSubscription, Long> {
    @Query("SELECT us FROM UserSubscription us WHERE us.user.userId = :userId ")
    Optional<UserSubscription> findUserSubscriptionByUserId(@Param("userId") Long userId);

}
