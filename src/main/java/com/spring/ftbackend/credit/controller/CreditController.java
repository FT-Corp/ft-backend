package com.spring.ftbackend.credit.controller;

import com.spring.ftbackend.credit.dto.request.CreditSaveRequest;
import com.spring.ftbackend.credit.dto.request.UserCreditSaveRequest;
import com.spring.ftbackend.credit.dto.response.CreditResponse;
import com.spring.ftbackend.credit.service.CreditService;
import com.spring.ftbackend.credit.service.UserCreditService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/credit")
public class CreditController {
    private final CreditService creditService;
    private final UserCreditService userCreditService;

    @Operation(summary = "크레딧 등록")
    @PostMapping
    public ResponseEntity<CreditResponse> saveCredit(@RequestBody CreditSaveRequest request) {
        CreditResponse creditResponse = creditService.saveCredit(request);
        return ResponseEntity.ok(creditResponse);
    }

    @Operation(summary = "유저가 보유한 총 크레딧 액수 조회")
    @GetMapping("/user")
    public ResponseEntity<Integer> getUserCredit(@RequestParam Long userId) {
        Integer totalCreditAmount = userCreditService.getUserCreditAmount(userId);
        return ResponseEntity.ok(totalCreditAmount);
    }

    @Operation(summary = "유저에게 크레딧 등록")
    @PostMapping("/user")
    public ResponseEntity<CreditResponse> registerUserCredit(@RequestBody UserCreditSaveRequest request) {
        userCreditService.registerUserCredit(request);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "유저가 보유한 크레딧 건수 조회")
    @GetMapping("/user/list")
    public ResponseEntity<List<CreditResponse>> getUserCreditList(@RequestParam Long userId) {
        List<CreditResponse> creditResponseList = userCreditService.getUserCreditList(userId);
        return ResponseEntity.ok(creditResponseList);
    }

}
