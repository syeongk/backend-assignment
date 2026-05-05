package com.sy.backendassignment.domain.order.entity;

import com.sy.backendassignment.domain.common.BaseEntity;
import com.sy.backendassignment.domain.discount.entity.AppliedDiscount;
import com.sy.backendassignment.domain.member.entity.Member;
import com.sy.backendassignment.domain.order.PaymentMethod;
import com.sy.backendassignment.domain.order.PaymentStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.sy.backendassignment.domain.order.PaymentStatus.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SuperBuilder
public class Payment extends BaseEntity {
    // 결제 방법
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private PaymentMethod paymentMethod;

    // 최종 결제금액
    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal amount;

    // 결제 상태
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PaymentStatus paymentStatus;

    // 결제 일시
    private LocalDateTime paidAt;

    // 주문 엔티티
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    // 회원 엔티티
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    // 적용된 할인 목록
    @OneToMany(mappedBy = "payment", cascade = CascadeType.PERSIST)
    private List<AppliedDiscount> appliedDiscounts = new ArrayList<>();

    public static Payment createPayment(Member member, Order order, BigDecimal amount, PaymentMethod method) {
        return Payment.builder()
                .paymentMethod(method)
                .amount(amount)
                .paymentStatus(READY)
                .order(order)
                .member(member)
                .build();
    }

    public void complete() {
        this.paymentStatus = COMPLETED;
        this.paidAt = LocalDateTime.now();
    }

    public void fail() {
        this.paymentStatus = FAILED;
    }
}
