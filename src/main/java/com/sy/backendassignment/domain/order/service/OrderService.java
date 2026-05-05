package com.sy.backendassignment.domain.order.service;

import com.sy.backendassignment.domain.member.entity.Grade;
import com.sy.backendassignment.domain.member.entity.Member;
import com.sy.backendassignment.domain.order.PaymentMethod;
import com.sy.backendassignment.domain.order.entity.Item;
import com.sy.backendassignment.domain.order.entity.Order;
import com.sy.backendassignment.domain.order.entity.OrderItem;
import com.sy.backendassignment.domain.order.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    // 주문서 생성
    @Transactional
    public Order createOrder(Member member, Item item, PaymentMethod paymentMethod) {
        // Item 을 OrderItem 으로 변환
        OrderItem orderItem = OrderItem.createOrderItem(item);

        // Order 객체 생성
        Order order = Order.createOrder(member, orderItem, paymentMethod);

        // Order 객체 저장
        return orderRepository.save(order);
    }

    // 회원 등급에 따라 할인 정책 적용된 결제 금액 계산
    public BigDecimal calculatePaymentAmount(Member member, Order order, PaymentMethod paymentMethod) {
        BigDecimal orderCost = order.getCost();
        Grade grade = member.getGrade();

        // 상품 할인 금액 계산
        BigDecimal discountAmount = grade.calculateDiscountAmount(orderCost);

        // 결제 금액 계산
        BigDecimal paymentAmount = orderCost.subtract(discountAmount);

        // 결제 금액 마이너스 방지
        paymentAmount = paymentAmount.max(BigDecimal.ZERO);

        return paymentAmount.setScale(0, RoundingMode.HALF_UP);
    }
}
