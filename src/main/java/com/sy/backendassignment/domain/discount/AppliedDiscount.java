package com.sy.backendassignment.domain.discount;

import com.sy.backendassignment.domain.common.BaseEntity;
import com.sy.backendassignment.domain.member.GradeType;
import com.sy.backendassignment.domain.order.Payment;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SuperBuilder
public class AppliedDiscount extends BaseEntity {
    // 결제시점 회원등급
    @Column(nullable = false, length = 30)
    private GradeType gradeType;

    // 할인정책명 (VIP 등급 할인, 포인트 중복 할인)
    @Column(nullable = false, length = 50)
    private String name;

    // 할인값
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal discountValue;

    // 할인단위
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private DiscountUnit discountUnit;

    // 할인종류
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private DiscountType discountType;

    // 할인총액
    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal amount;

    // 결제 엔티티
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id", nullable = false)
    private Payment payment;

    // 할인 엔티티
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "discount_policy_id", nullable = false)
    private DiscountPolicy discountPolicy;
}
