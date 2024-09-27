package com.spring.ftbackend.Subscription.controller;


import com.spring.ftbackend.Subscription.domain.Subscription;
import com.spring.ftbackend.Subscription.dto.request.SubscriptionSaveRequestDto;
import com.spring.ftbackend.Subscription.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/subscription")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @GetMapping
    public ResponseEntity<List<Subscription>> getAllSubscriptions() {
        log.info("getAllSubscriptions");
        return ResponseEntity.ok().body(subscriptionService.getAllSubscriptions());
    }

    @PostMapping
    public ResponseEntity<Void> saveSubscription(@RequestBody SubscriptionSaveRequestDto subscriptionSaveRequestDto) {
        log.info(subscriptionSaveRequestDto.getSubscriptionType());
//        need common responseEntity (DTO)
        boolean isSaved = subscriptionService.saveSubscription(subscriptionSaveRequestDto);
        if (isSaved) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        else{
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

//    @PostMapping
//    public ResponseEntity<Subscription> getSubscriptionDetail(@RequestBody String subscriptionType) {
//
//    }




}
