package com.sy.backendassignment.domain.order;

import com.sy.backendassignment.domain.discount.entity.AppliedDiscount;
import com.sy.backendassignment.domain.member.entity.Member;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class PaymentDiscountHandler implements DiscountHandler {
    @Override
    public AppliedDiscount applyDiscount(Member member, BigDecimal orderItemPrice) {
        return null;
    }
}
