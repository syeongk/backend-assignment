package com.sy.backendassignment.domain.member;

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
}

