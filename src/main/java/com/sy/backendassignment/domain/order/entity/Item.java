package com.sy.backendassignment.domain.order.entity;

import com.sy.backendassignment.domain.common.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
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
public class Item extends BaseEntity {
    // 상품명
    @Column(nullable = false, length = 50)
    private String name;

    // 상품가격
    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal price;

    // 주문한 상품 목록
    @OneToMany(mappedBy = "item", cascade = CascadeType.PERSIST)
    private List<OrderItem> orderItems = new ArrayList<>();
}
