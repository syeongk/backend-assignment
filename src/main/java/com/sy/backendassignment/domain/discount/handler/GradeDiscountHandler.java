package com.sy.backendassignment.domain.discount.handler;

import com.sy.backendassignment.domain.discount.DiscountRequest;
import com.sy.backendassignment.domain.discount.entity.AppliedDiscount;
import com.sy.backendassignment.domain.discount.entity.DiscountPolicy;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import static com.sy.backendassignment.domain.discount.entity.AppliedDiscount.createAppliedDiscount;

@Component
public class GradeDiscountHandler implements DiscountHandler {
    @Override
    public AppliedDiscount applyDiscount(DiscountRequest request) {
        DiscountPolicy discountPolicy = request.member().getDiscountPolicy();

        // 할인 금액 계산
        BigDecimal discountAmount = discountPolicy.calculateDiscountAmount(request.originalPrice());

        // 적용된 할인 기록
        return createAppliedDiscount(request.member(), discountPolicy, discountAmount);
    }
}
