package com.spring.ftbackend.subscription.controller;

import com.spring.ftbackend.subscription.domain.UserSubscription;
import com.spring.ftbackend.subscription.dto.request.UserSubscriptionInfoRequestDto;
import com.spring.ftbackend.subscription.dto.request.UserSubscriptionSaveRequestDto;
import com.spring.ftbackend.subscription.dto.response.UserSubscriptionInfoResponseDto;
import com.spring.ftbackend.subscription.service.SubscriptionService;
import com.spring.ftbackend.subscription.service.UserSubscriptionService;
import com.spring.ftbackend.login.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/userSubscription")
public class UserSubscriptionController {
    private final SubscriptionService subscriptionService;
    private final UserService userService;
    private final UserSubscriptionService userSubscriptionService;


    //userSubscription 생성
    @PostMapping
    public ResponseEntity<UserSubscription> createUserSubscription(@RequestBody UserSubscriptionSaveRequestDto userSubscriptionSaveRequestDto) {
        log.info(String.valueOf(userSubscriptionSaveRequestDto.getUserId()));
        log.info(String.valueOf(userSubscriptionSaveRequestDto.getSubscriptionId()));
        boolean isSaved = userSubscriptionService.saveUserSubscription(userSubscriptionSaveRequestDto);
        if (isSaved) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        else{
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PostMapping(value = "/info",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserSubscriptionInfoResponseDto> getUserSubscription(@RequestBody UserSubscriptionInfoRequestDto userSubscriptionInfoRequestDto){
        return ResponseEntity.ok()
                .body(userSubscriptionService.getUserSubscription(userSubscriptionInfoRequestDto));
    }

}
