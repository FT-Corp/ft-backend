package com.spring.ftbackend.subscription.service;

import com.spring.ftbackend.subscription.domain.Subscription;
import com.spring.ftbackend.subscription.dto.request.SubscriptionSaveRequestDto;
import com.spring.ftbackend.subscription.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

    public boolean saveSubscription(SubscriptionSaveRequestDto subscriptionSaveRequestDto) {
        System.out.println(subscriptionSaveRequestDto);
        Subscription.SubscriptionType subscriptionType = Subscription.SubscriptionType.from(subscriptionSaveRequestDto.getSubscriptionType());
        Optional<Subscription> subscriptionOpt = subscriptionRepository.findBySubscriptionType(subscriptionType);
        System.out.println("1");
        if (subscriptionOpt.isEmpty()){
            subscriptionRepository.save(Subscription.from(subscriptionSaveRequestDto));
            return true;
        }

        else {
//            return ResponseEntity.status(HttpStatus.CONFLICT).build();
            return false;
        }
    }

    public List<Subscription> getAllSubscriptions(){
        return subscriptionRepository.findAll();
    }
}
