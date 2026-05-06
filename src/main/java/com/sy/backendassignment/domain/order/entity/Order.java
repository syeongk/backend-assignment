package com.sy.backendassignment.domain.order.entity;

import com.sy.backendassignment.domain.common.BaseEntity;
import com.sy.backendassignment.domain.discount.entity.AppliedDiscount;
import com.sy.backendassignment.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SuperBuilder
@Table(name = "orders")
public class Order extends BaseEntity {
    // 주문원가
    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal cost;

    // 결제금액
    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal paymentAmount;

    // 주문한 상품 목록
    @Builder.Default
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    // 회원 엔티티
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    // 결제 엔티티
    @OneToOne(mappedBy = "order", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private Payment payment;

    // 적용된 할인 목록
    @Builder.Default
    @OneToMany(mappedBy = "order", cascade = CascadeType.PERSIST)
    private List<AppliedDiscount> appliedDiscounts = new ArrayList<>();

    public static BigDecimal calculatePaymentAmount(BigDecimal originalPrice, AppliedDiscount appliedDiscount) {
        // 결제 금액 계산 및 마이너스 값 방지
        BigDecimal paymentAmount = originalPrice.subtract(appliedDiscount.getDiscountAmount());
        return paymentAmount.max(BigDecimal.ZERO).setScale(0, RoundingMode.HALF_UP);
    }

    // 주문 객체 생성
    public static Order createOrder(Member member, OrderItem orderItem, AppliedDiscount appliedDiscount) {
        Order order = Order.builder()
                .cost(orderItem.getPrice())
                .member(member)
                .paymentAmount(calculatePaymentAmount(orderItem.getPrice(), appliedDiscount))
                .build();
        order.addOrderItem(orderItem);
        order.addAppliedDiscount(appliedDiscount);

        return order;
    }

    // 연관관계 편의 메서드
    public void addOrderItem(OrderItem orderItem) {
        this.orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    // 연관관계 편의 메서드
    public void addAppliedDiscount(AppliedDiscount appliedDiscount) {
        this.appliedDiscounts.add(appliedDiscount);
        appliedDiscount.setOrder(this);
    }

    public void applyExtraDiscount(AppliedDiscount appliedDiscount) {
        this.paymentAmount = calculatePaymentAmount(this.paymentAmount, appliedDiscount);
        addAppliedDiscount(appliedDiscount);
    }
}
