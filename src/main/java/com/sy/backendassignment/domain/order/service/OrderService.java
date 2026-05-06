package com.sy.backendassignment.domain.order.service;

import com.sy.backendassignment.domain.discount.DiscountRequest;
import com.sy.backendassignment.domain.discount.entity.AppliedDiscount;
import com.sy.backendassignment.domain.discount.strategy.GradeDiscountStrategy;
import com.sy.backendassignment.domain.discount.strategy.PaymentDiscountStrategy;
import com.sy.backendassignment.domain.member.entity.Member;
import com.sy.backendassignment.domain.member.repository.MemberRepository;
import com.sy.backendassignment.domain.order.PaymentMethod;
import com.sy.backendassignment.domain.order.entity.Item;
import com.sy.backendassignment.domain.order.entity.Order;
import com.sy.backendassignment.domain.order.entity.OrderItem;
import com.sy.backendassignment.domain.order.repository.OrderRepository;
import com.sy.backendassignment.exception.BusinessException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static com.sy.backendassignment.exception.ErrorCode.INVALID_ITEM;
import static com.sy.backendassignment.exception.ErrorCode.MEMBER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final PaymentDiscountStrategy paymentDiscountStrategy;
    private final GradeDiscountStrategy gradeDiscountStrategy;

    // 주문서 생성
    @Transactional
    public Order createOrder(Member member, Item item) {
        if (member == null) {
            throw new BusinessException(MEMBER_NOT_FOUND);
        }

        if (item == null || item.getIsDeleted()) {
            throw new BusinessException(INVALID_ITEM);
        }

        // Item 을 OrderItem 으로 변환
        OrderItem orderItem = OrderItem.createOrderItem(item);
        BigDecimal orderItemPrice = orderItem.getPrice();

        // 등급에 따른 할인 정책 우선 적용
        AppliedDiscount appliedDiscount = gradeDiscountStrategy.applyDiscount(DiscountRequest.from(member, orderItemPrice));

        // Order 객체 생성
        Order order = Order.createOrder(member, orderItem, appliedDiscount);

        // Order 객체 저장
        return orderRepository.save(order);
    }

    // 결제 수단 선택 시 추가 중복 할인 정책 적용
    @Transactional
    public void applyPaymentMethod(Member member, Order order, PaymentMethod paymentMethod) {
        // 결제 수단에 따른 할인 정책 적용
        AppliedDiscount appliedDiscount = paymentDiscountStrategy.applyDiscount(DiscountRequest.from(member, order.getPaymentAmount(), paymentMethod));

        // 주문에 할인 정책 반영
        order.applyExtraDiscount(appliedDiscount);
    }
}
