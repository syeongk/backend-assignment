package com.sy.backendassignment.domain.order.entity;

import com.sy.backendassignment.domain.common.BaseEntity;
import com.sy.backendassignment.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
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

    // 주문 객체 생성
    public static Order createOrder(Member member, OrderItem orderItem, BigDecimal paymentAmount) {
        Order order = Order.builder()
                .cost(orderItem.getPrice())
                .member(member)
                .paymentAmount(paymentAmount)
                .build();
        order.addOrderItem(orderItem);

        return order;
    }

    // 연관관계 편의 메서드
    public void addOrderItem(OrderItem orderItem) {
        this.orderItems.add(orderItem);
        orderItem.setOrder(this);
    }
}
