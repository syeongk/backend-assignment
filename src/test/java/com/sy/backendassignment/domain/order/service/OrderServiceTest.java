package com.sy.backendassignment.domain.order.service;

import com.sy.backendassignment.domain.discount.DiscountUnit;
import com.sy.backendassignment.domain.discount.entity.AppliedDiscount;
import com.sy.backendassignment.domain.discount.entity.DiscountPolicy;
import com.sy.backendassignment.domain.discount.handler.GradeDiscountHandler;
import com.sy.backendassignment.domain.discount.handler.PaymentDiscountHandler;
import com.sy.backendassignment.domain.member.GradeType;
import com.sy.backendassignment.domain.member.entity.Grade;
import com.sy.backendassignment.domain.member.entity.Member;
import com.sy.backendassignment.domain.member.repository.MemberRepository;
import com.sy.backendassignment.domain.order.entity.Item;
import com.sy.backendassignment.domain.order.entity.Order;
import com.sy.backendassignment.domain.order.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static com.sy.backendassignment.domain.order.PaymentMethod.POINT;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private PaymentDiscountHandler paymentDiscountHandler;

    @Mock
    private GradeDiscountHandler gradeDiscountHandler;

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
    @DisplayName("calculatePaymentAmount")
    class CalculatePaymentAmount {

        @Test
        void VIP_등급은_1000원_할인된다() {
            // given
            Item item = mock(Item.class);
            given(item.getPrice()).willReturn(BigDecimal.valueOf(10000));

            given(orderRepository.save(any(Order.class))).willAnswer(invocation -> invocation.getArgument(0));

            AppliedDiscount gradeDiscount = AppliedDiscount.builder()
                    .name("VIP 등급 할인")
                    .discountAmount(BigDecimal.valueOf(1000))
                    .build();
            given(gradeDiscountHandler.applyDiscount(any())).willReturn(gradeDiscount);

            // when
            Order order = orderService.createOrder(vipMember, item);

            // then
            assertThat(order.getPaymentAmount()).isEqualByComparingTo(BigDecimal.valueOf(9000));
        }

        @Test
        void VVIP_등급은_10퍼센트_할인된다() {
            // given
            Item item = mock(Item.class);
            given(item.getPrice()).willReturn(BigDecimal.valueOf(50000));

            given(orderRepository.save(any(Order.class))).willAnswer(invocation -> invocation.getArgument(0));

            AppliedDiscount gradeDiscount = AppliedDiscount.builder()
                    .name("VVIP 등급 할인")
                    .discountAmount(BigDecimal.valueOf(5000))
                    .build();
            given(gradeDiscountHandler.applyDiscount(any())).willReturn(gradeDiscount);

            // when
            Order order = orderService.createOrder(normalMember, item);

            // then
            assertThat(order.getPaymentAmount()).isEqualByComparingTo(BigDecimal.valueOf(45000));
        }

        @Test
        void 일반_등급은_할인되지_않는다() {
            // given
            Item item = mock(Item.class);
            given(item.getPrice()).willReturn(BigDecimal.valueOf(10000));

            given(orderRepository.save(any(Order.class))).willAnswer(invocation -> invocation.getArgument(0));

            AppliedDiscount gradeDiscount = AppliedDiscount.builder()
                    .name("NORMAL 등급 할인")
                    .discountAmount(BigDecimal.valueOf(0))
                    .build();
            given(gradeDiscountHandler.applyDiscount(any())).willReturn(gradeDiscount);

            // when
            Order order = orderService.createOrder(normalMember, item);

            // then
            assertThat(order.getPaymentAmount()).isEqualByComparingTo(BigDecimal.valueOf(10000));
        }

        @Test
        void 포인트_결제_시_중복할인_된다() {
            // given
            Item item = mock(Item.class);
            given(item.getPrice()).willReturn(BigDecimal.valueOf(10000));

            given(orderRepository.save(any(Order.class))).willAnswer(invocation -> invocation.getArgument(0));

            AppliedDiscount gradeDiscount = AppliedDiscount.builder()
                    .name("VIP 등급 할인")
                    .discountAmount(BigDecimal.valueOf(1000))
                    .build();
            given(gradeDiscountHandler.applyDiscount(any())).willReturn(gradeDiscount);

            AppliedDiscount pointDiscount = AppliedDiscount.builder()
                    .name("포인트 추가 중복 할인")
                    .discountAmount(BigDecimal.valueOf(450))
                    .build();
            given(paymentDiscountHandler.applyDiscount(any())).willReturn(pointDiscount);

            // when : 주문 생성 및 결제 수단 적용
            Order order = orderService.createOrder(vipMember, item);
            orderService.applyPaymentMethod(vipMember, order, POINT);

            // then
            assertThat(order.getPaymentAmount()).isEqualByComparingTo(BigDecimal.valueOf(8550));
        }
    }
}

