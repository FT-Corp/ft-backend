package com.spring.ftbackend.coupon.service;


import com.spring.ftbackend.coupon.domain.Coupon;
import com.spring.ftbackend.coupon.domain.UserCoupon;
import com.spring.ftbackend.coupon.dto.request.UserCouponSaveRequest;
import com.spring.ftbackend.coupon.dto.response.CouponResponse;
import com.spring.ftbackend.coupon.repository.CouponRepository;
import com.spring.ftbackend.coupon.repository.UserCouponRepository;
import com.spring.ftbackend.user.Repository.UserRepository;
import com.spring.ftbackend.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserCouponService {
    private final UserCouponRepository userCouponRepository;
    private final UserRepository userRepository;
    private final CouponRepository couponRepository;

    public Integer getUserCouponAmount(Long userId) {
        return userCouponRepository.findUserCouponCountByUserId(userId);
    }

    public void registerUserCoupon(UserCouponSaveRequest request) {
        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid user"));
        Coupon coupon = couponRepository.findById(request.couponId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid coupon"));
        UserCoupon userCoupon = UserCoupon.builder()
                .user(user)
                .coupon(coupon)
                .build();
        userCouponRepository.save(userCoupon);
    }

    public List<CouponResponse> getUserCouponList(Long userId) {
        return userCouponRepository.findUserCouponsByUserId(userId)
                .stream()
                .map(userCoupon -> mapToCouponResponse(userCoupon.getCoupon()))
                .collect(Collectors.toList());
    }
    private CouponResponse mapToCouponResponse(Coupon coupon) {
        return CouponResponse.builder()
                .context(coupon.getContext())
                .bookLimit(coupon.getBookLimit())
                .issueDate(coupon.getIssueDate())
                .expireDate(coupon.getExpireDate())
                .redeemDate(coupon.getRedeemDate())
                .discountRate(coupon.getDiscountRate())
                .build();
    }


}
