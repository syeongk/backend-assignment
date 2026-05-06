package com.sy.backendassignment.domain.order.service;

import com.sy.backendassignment.domain.discount.DiscountType;
import com.sy.backendassignment.domain.discount.DiscountUnit;
import com.sy.backendassignment.domain.discount.entity.AppliedDiscount;
import com.sy.backendassignment.domain.discount.entity.DiscountPolicy;
import com.sy.backendassignment.domain.discount.repository.DiscountPolicyRepository;
import com.sy.backendassignment.domain.member.GradeType;
import com.sy.backendassignment.domain.member.entity.Grade;
import com.sy.backendassignment.domain.member.entity.Member;
import com.sy.backendassignment.domain.member.repository.GradeRepository;
import com.sy.backendassignment.domain.member.repository.MemberRepository;
import com.sy.backendassignment.domain.order.entity.Item;
import com.sy.backendassignment.domain.order.entity.Order;
import com.sy.backendassignment.domain.order.repository.ItemRepository;
import com.sy.backendassignment.domain.order.repository.OrderRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class HistoryTest {

    @Autowired
    private EntityManager entityManager;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private DiscountPolicyRepository discountPolicyRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private GradeRepository gradeRepository;

    private Member vvipMember;
    private DiscountPolicy vvipPolicy;
    private Item lectureItem;

    @BeforeEach
    void setUp() {
        // VVIP 할인 정책 저장 (10% 할인)
        vvipPolicy = DiscountPolicy.builder()
                .name("VVIP 등급 할인")
                .discountValue(new BigDecimal("10"))
                .discountUnit(DiscountUnit.PERCENT)
                .discountType(DiscountType.GRADE)
                .isDeleted(false)
                .version(1)
                .startAt(LocalDateTime.now())
                .build();
        discountPolicyRepository.save(vvipPolicy);

        // VVIP 등급 저장
        Grade vvipGrade = Grade.builder()
                .gradeType(GradeType.VVIP)
                .discountPolicy(vvipPolicy)
                .isDeleted(false)
                .build();
        gradeRepository.save(vvipGrade);

        // VVIP 회원 저장
        vvipMember = Member.builder()
                .name("이소영")
                .point(new BigDecimal("1000000"))
                .grade(vvipGrade)
                .isDeleted(false)
                .build();
        memberRepository.save(vvipMember);

        // 상품 저장
        lectureItem = Item.builder()
                .name("일반 강의")
                .price(new BigDecimal("5999"))
                .isDeleted(false)
                .build();
        itemRepository.save(lectureItem);

        entityManager.flush();
        entityManager.clear();
    }

    @Test
    void 정책_수정_후에도_기존_할인이력은_유지되어야_한다() {
        // given : 주문 생성, VVIP 10% 할인 적용
        Member member = memberRepository.findById(vvipMember.getId()).orElseThrow();
        Item item = itemRepository.findById(lectureItem.getId()).orElseThrow();

        Order order = orderService.createOrder(member, item);
        Long orderId = order.getId();

        // when : 원본 정책 수정 10% -> 20%
        DiscountPolicy policy = discountPolicyRepository.findById(vvipPolicy.getId()).orElseThrow();
        policy.updateName("VVIP 20% 할인");
        policy.updateDiscountValue(new BigDecimal("20"));
        discountPolicyRepository.saveAndFlush(policy);

        entityManager.clear();

        // then : 과거 주문 이력 조회 및 검증
        Order savedOrder = orderRepository.findById(orderId).orElseThrow();
        AppliedDiscount history = savedOrder.getAppliedDiscounts().get(0);

        assertThat(history.getName()).isEqualTo("VVIP 등급 할인");
        assertThat(history.getDiscountValue()).isEqualByComparingTo(new BigDecimal("10"));
    }

    @Test
    void 정책_삭제_후에도_기존_할인이력은_유지되어야_한다() {
        // given : 주문 생성, VVIP 10% 할인 적용
        Member member = memberRepository.findById(vvipMember.getId()).orElseThrow();
        Item item = itemRepository.findById(lectureItem.getId()).orElseThrow();

        Order order = orderService.createOrder(member, item);
        Long orderId = order.getId();
        Long policyId = vvipPolicy.getId();

        // when : 정책 삭제 (soft delete)
        DiscountPolicy policy = discountPolicyRepository.findById(policyId).orElseThrow();
        policy.delete();
        discountPolicyRepository.saveAndFlush(policy);

        entityManager.clear();

        // then : 과거 주문 이력 조회 및 검증
        Order savedOrder = orderRepository.findById(orderId).orElseThrow();
        AppliedDiscount history = savedOrder.getAppliedDiscounts().get(0);

        assertThat(history.getName()).isEqualTo("VVIP 등급 할인");
        assertThat(history.getDiscountValue()).isEqualByComparingTo(new BigDecimal("10"));
    }
}
