package com.sy.backendassignment.domain.order;

import com.sy.backendassignment.domain.discount.entity.AppliedDiscount;
import com.sy.backendassignment.domain.member.entity.Member;

import java.math.BigDecimal;

public interface DiscountHandler {
    AppliedDiscount applyDiscount(Member member, BigDecimal orderItemPrice);
}
