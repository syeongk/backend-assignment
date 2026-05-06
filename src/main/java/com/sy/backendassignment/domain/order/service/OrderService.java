package com.sy.backendassignment.domain.order.service;

import com.sy.backendassignment.domain.discount.entity.AppliedDiscount;
import com.sy.backendassignment.domain.member.entity.Member;
import com.sy.backendassignment.domain.order.GradeDiscountHandler;
import com.sy.backendassignment.domain.order.entity.Item;
import com.sy.backendassignment.domain.order.entity.Order;
import com.sy.backendassignment.domain.order.entity.OrderItem;
import com.sy.backendassignment.domain.order.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final GradeDiscountHandler gradeDiscountHandler;

    // 주문서 생성
    @Transactional
    public Order createOrder(Member member, Item item) {
        // Item 을 OrderItem 으로 변환
        OrderItem orderItem = OrderItem.createOrderItem(item);
        BigDecimal orderItemPrice = orderItem.getPrice();

        // 등급에 따른 할인 정책 우선 적용
        AppliedDiscount appliedDiscount = gradeDiscountHandler.applyDiscount(member, orderItemPrice);

        // Order 객체 생성
        Order order = Order.createOrder(member, orderItem, appliedDiscount);

        // Order 객체 저장
        return orderRepository.save(order);
    }
}
