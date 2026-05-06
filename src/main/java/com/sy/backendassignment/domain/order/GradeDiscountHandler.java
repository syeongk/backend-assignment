package com.sy.backendassignment.domain.order;

import com.sy.backendassignment.domain.discount.entity.AppliedDiscount;
import com.sy.backendassignment.domain.discount.entity.DiscountPolicy;
import com.sy.backendassignment.domain.member.entity.Member;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import static com.sy.backendassignment.domain.discount.entity.AppliedDiscount.createAppliedDiscount;

@Component
public class GradeDiscountHandler implements DiscountHandler {
    @Override
    public AppliedDiscount applyDiscount(Member member, BigDecimal orderItemPrice) {
        DiscountPolicy discountPolicy = member.getDiscountPolicy();

        // 할인 금액 계산
        BigDecimal discountAmount = discountPolicy.calculateDiscountAmount(orderItemPrice);

        // 적용된 할인 기록
        return createAppliedDiscount(member, discountPolicy, discountAmount);
    }
}
