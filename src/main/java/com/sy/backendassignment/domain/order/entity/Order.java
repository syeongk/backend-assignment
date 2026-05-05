package com.sy.backendassignment.domain.order.entity;

import com.sy.backendassignment.domain.common.BaseEntity;
import com.sy.backendassignment.domain.member.entity.Member;
import com.sy.backendassignment.domain.order.PaymentMethod;
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
@Table(name = "orders")
public class Order extends BaseEntity {
    // 주문원가
    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal cost;

    // 주문한 상품 목록
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
    public static Order createOrder(Member member, OrderItem orderItem, PaymentMethod method) {
        Order order = Order.builder()
                .cost(orderItem.getOrderItemPrice())
                .member(member)
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
