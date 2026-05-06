package com.sy.backendassignment.domain.member.entity;

import com.sy.backendassignment.domain.common.BaseEntity;
import com.sy.backendassignment.domain.order.entity.Order;
import com.sy.backendassignment.domain.order.entity.Payment;
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
public class Member extends BaseEntity {
    // 회원명
    @Column(nullable = false, length = 30)
    private String name;

    // 보유 포인트
    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal point;

    // 등급 엔티티
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grade_id", nullable = false)
    private Grade grade;

    @Builder.Default
    @OneToMany(mappedBy = "member", cascade = CascadeType.PERSIST)
    private List<Order> orders = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "member", cascade = CascadeType.PERSIST)
    private List<Payment> payments = new ArrayList<>();
}

