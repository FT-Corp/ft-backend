package com.spring.ftbackend.subscription.repository;

import com.spring.ftbackend.subscription.domain.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    Optional<Subscription> findBySubscriptionType(Subscription.SubscriptionType subscriptionType);

}
