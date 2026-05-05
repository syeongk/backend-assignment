package com.sy.backendassignment.domain.order;

import com.sy.backendassignment.domain.common.BaseEntity;
import com.sy.backendassignment.domain.discount.AppliedDiscount;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SuperBuilder
public class Payment extends BaseEntity {
    // 최종 결제금액
    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal amount;

    // 결제 상태
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PaymentStatus paymentStatus;

    // 주문 엔티티
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    // 적용된 할인 목록
    @OneToMany(mappedBy = "payment", cascade = CascadeType.PERSIST)
    private List<AppliedDiscount> appliedDiscounts = new ArrayList<>();
}
