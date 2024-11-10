package com.spring.ftbackend.coupon.service;


import com.spring.ftbackend.coupon.domain.Coupon;
import com.spring.ftbackend.coupon.dto.request.BookCouponSaveRequest;
import com.spring.ftbackend.coupon.dto.request.DiscountCouponSaveRequest;
import com.spring.ftbackend.coupon.dto.response.CouponResponse;
import com.spring.ftbackend.coupon.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class CouponService {
    private final CouponRepository couponRepository;

    public CouponResponse saveBookCoupon(BookCouponSaveRequest request) {
        Coupon coupon = Coupon.builder()
                .context(request.context())
                .bookLimit(request.bookLimit())
                .build();
        couponRepository.save(coupon);
        coupon.setExpireDate(request.duration());
        couponRepository.save(coupon);

        return mapToCouponResponse(coupon);
    }

    public CouponResponse saveDiscountCoupon(DiscountCouponSaveRequest request) {
        //Coupon 부모class 생성 후 type 나누기
        Coupon coupon = Coupon.builder()
                .context(request.context())
                .discountRate(request.discountRate())
                .discountAmount(request.discountAmount())
                .build();

        couponRepository.save(coupon);
        coupon.setExpireDate(request.duration());
        couponRepository.save(coupon);

        return mapToCouponResponse(coupon);
    }

    public void useCoupon(Integer couponId){

    }

    private CouponResponse mapToCouponResponse(Coupon coupon) {
        return CouponResponse.builder()
                .context(coupon.getContext())
                .bookLimit(coupon.getBookLimit())
                .issueDate(coupon.getIssueDate())
                .expireDate(coupon.getExpireDate())
                .redeemDate(coupon.getRedeemDate())
                .discountRate(coupon.getDiscountRate())
                .discountAmount(coupon.getDiscountAmount())
                .build();
    }

}
