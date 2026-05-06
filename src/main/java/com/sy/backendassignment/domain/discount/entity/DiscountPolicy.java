package com.sy.backendassignment.domain.discount.entity;

import com.sy.backendassignment.domain.common.BaseEntity;
import com.sy.backendassignment.domain.discount.DiscountType;
import com.sy.backendassignment.domain.discount.DiscountUnit;
import com.sy.backendassignment.domain.member.entity.Grade;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.sy.backendassignment.domain.discount.DiscountUnit.WON;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SuperBuilder
public class DiscountPolicy extends BaseEntity {
    // 할인정책버전
    @Column(nullable = false)
    private Integer version;

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

    // 정책 시작일
    @Column(nullable = false)
    private LocalDateTime startAt;

    // 정책 종료일
    private LocalDateTime endAt;

    // 등급 목록
    @Builder.Default
    @OneToMany(mappedBy = "discountPolicy", cascade = CascadeType.PERSIST)
    private List<Grade> grades = new ArrayList<>();

    // 적용된 할인 목록
    @Builder.Default
    @OneToMany(mappedBy = "discountPolicy", cascade = CascadeType.PERSIST)
    private List<AppliedDiscount> appliedDiscounts = new ArrayList<>();

    // 할인 금액 계산
    public BigDecimal calculateDiscountAmount(BigDecimal price) {
        if (this.discountUnit.equals(WON)) {
            return this.discountValue;
        } else {
            return price.multiply(this.discountValue).divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
        }
    }
}
