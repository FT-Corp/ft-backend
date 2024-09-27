package com.spring.ftbackend.Subscription.repository;

import com.spring.ftbackend.Subscription.domain.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    Optional<Subscription> findBySubscriptionType(Subscription.SubscriptionType subscriptionType);

}
