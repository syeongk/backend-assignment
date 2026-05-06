package com.sy.backendassignment.domain.discount;

import com.sy.backendassignment.domain.member.entity.Member;
import com.sy.backendassignment.domain.order.PaymentMethod;

import java.math.BigDecimal;

public record DiscountRequest(
        Member member,
        BigDecimal originalPrice,
        PaymentMethod paymentMethod
) {
    public static DiscountRequest from(Member member, BigDecimal orderItemPrice) {
        return new DiscountRequest(member, orderItemPrice, null);
    }

    public static DiscountRequest from(Member member, BigDecimal originalPrice, PaymentMethod paymentMethod) {
        return new DiscountRequest(member, originalPrice, paymentMethod);
    }
}
