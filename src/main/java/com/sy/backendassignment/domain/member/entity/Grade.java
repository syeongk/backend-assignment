package com.sy.backendassignment.domain.member.entity;

import com.sy.backendassignment.domain.common.BaseEntity;
import com.sy.backendassignment.domain.discount.entity.DiscountPolicy;
import com.sy.backendassignment.domain.member.GradeType;
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
public class Grade extends BaseEntity {
    // 등급 종류
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private GradeType gradeType;

    // 할인정책 엔티티
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "discount_policy_id", nullable = false)
    private DiscountPolicy discountPolicy;

    // 회원 목록
    @OneToMany(mappedBy = "grade", cascade = CascadeType.PERSIST)
    private List<Member> members = new ArrayList<>();

    public BigDecimal calculateDiscountAmount(BigDecimal price) {
        return this.discountPolicy.calculateDiscountAmount(price);
    }
}
