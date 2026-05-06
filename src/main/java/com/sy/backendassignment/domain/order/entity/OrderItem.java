package com.sy.backendassignment.domain.order.entity;

import com.sy.backendassignment.domain.common.BaseEntity;
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
public class OrderItem extends BaseEntity {
    // 주문 시점 가격
    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal price;

    // 주문 엔티티
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    // 상품 엔티티
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    // 초기 주문 아이템 객체 생성
    public static OrderItem createOrderItem(Item item) {
        return OrderItem.builder()
                .price(item.getPrice())
                .item(item)
                .build();
    }

    protected void setOrder(Order order) {
        this.order = order;
    }
}
