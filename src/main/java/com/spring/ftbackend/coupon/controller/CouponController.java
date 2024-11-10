package com.spring.ftbackend.coupon.controller;


import com.spring.ftbackend.coupon.dto.request.BookCouponSaveRequest;
import com.spring.ftbackend.coupon.dto.request.DiscountCouponSaveRequest;
import com.spring.ftbackend.coupon.dto.request.UserCouponSaveRequest;
import com.spring.ftbackend.coupon.dto.response.CouponResponse;
import com.spring.ftbackend.coupon.service.CouponService;
import com.spring.ftbackend.coupon.service.UserCouponService;
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
@RequestMapping("/coupon")
public class CouponController {
    private final CouponService couponService;
    private final UserCouponService userCouponService;

    @Operation(summary = "쿠폰 등록")
    @PostMapping("")
    public ResponseEntity<CouponResponse> saveBookCoupon(@RequestBody DiscountCouponSaveRequest request) {
        CouponResponse couponResponse = couponService.saveDiscountCoupon(request);
        return ResponseEntity.ok(couponResponse);
    }

    @Operation(summary = "유저가 보유한 쿠폰 갯수 조회")
    @GetMapping("/user")
    public ResponseEntity<Integer> getUserCoupon(@RequestParam Long userId) {
        Integer couponAmount = userCouponService.getUserCouponAmount(userId);
        return ResponseEntity.ok(couponAmount);
    }

    @Operation(summary = "유저에게 쿠폰 등록")
    @PostMapping("/user")
    public ResponseEntity<Void> registerUserCoupon(@RequestBody UserCouponSaveRequest request){
        userCouponService.registerUserCoupon(request);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "유저가 보유한 모든 쿠폰 조회")
    @GetMapping("/user/list")
    public ResponseEntity<List<CouponResponse>> getUserCouponList(@RequestParam Long userId) {
        List<CouponResponse> couponResponseList = userCouponService.getUserCouponList(userId);
        return ResponseEntity.ok(couponResponseList);
    }

}
