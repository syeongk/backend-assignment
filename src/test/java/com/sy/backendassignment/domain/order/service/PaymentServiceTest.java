package com.sy.backendassignment.domain.order.service;

import com.sy.backendassignment.domain.discount.DiscountUnit;
import com.sy.backendassignment.domain.discount.entity.DiscountPolicy;
import com.sy.backendassignment.domain.member.GradeType;
import com.sy.backendassignment.domain.member.entity.Grade;
import com.sy.backendassignment.domain.member.entity.Member;
import com.sy.backendassignment.domain.order.repository.OrderRepository;
import com.sy.backendassignment.domain.order.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {
    @InjectMocks
    private PaymentService paymentService;

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private OrderRepository orderRepository;

    private Member normalMember;
    private Member vipMember;
    private Member vvipMember;

    @BeforeEach
    void setUp() {
        // 할인 정책(DiscountPolicy) 생성
        DiscountPolicy normalPolicy = DiscountPolicy.builder()
                .name("NORMAL 등급 할인")
                .discountValue(BigDecimal.ZERO)
                .discountUnit(DiscountUnit.WON)
                .build();

        DiscountPolicy vipPolicy = DiscountPolicy.builder()
                .name("VIP 등급 할인")
                .discountValue(BigDecimal.valueOf(1000))
                .discountUnit(DiscountUnit.WON)
                .build();

        DiscountPolicy vvipPolicy = DiscountPolicy.builder()
                .name("VVIP 등급 할인")
                .discountValue(BigDecimal.valueOf(10))
                .discountUnit(DiscountUnit.PERCENT)
                .build();

        // 등급(Grade) 생성
        Grade normalGrade = Grade.builder()
                .gradeType(GradeType.NORMAL)
                .discountPolicy(normalPolicy)
                .build();

        Grade vipGrade = Grade.builder()
                .gradeType(GradeType.VIP)
                .discountPolicy(vipPolicy)
                .build();

        Grade vvipGrade = Grade.builder()
                .gradeType(GradeType.VVIP)
                .discountPolicy(vvipPolicy)
                .build();

        // 회원(Member) 생성
        normalMember = Member.builder()
                .name("김서영")
                .point(BigDecimal.valueOf(10000))
                .grade(normalGrade)
                .build();

        vipMember = Member.builder()
                .name("박소영")
                .point(BigDecimal.valueOf(100000))
                .grade(vipGrade)
                .build();

        vvipMember = Member.builder()
                .name("이소영")
                .point(BigDecimal.valueOf(1000000))
                .grade(vvipGrade)
                .build();
    }

    @Nested
    @DisplayName("pay")
    class pay {
        @Test
        void 결제완료_시_적용된_할인은_DB에_저장한다() {

        }
    }
}
