package com.sy.backendassignment.domain.order.service;

import com.sy.backendassignment.domain.member.entity.Member;
import com.sy.backendassignment.domain.order.PaymentMethod;
import com.sy.backendassignment.domain.order.entity.Order;
import com.sy.backendassignment.domain.order.entity.Payment;
import com.sy.backendassignment.domain.order.repository.OrderRepository;
import com.sy.backendassignment.domain.order.repository.PaymentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.sy.backendassignment.domain.order.entity.Payment.createPayment;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;

    @Transactional
    public void pay(Member member, Order order, PaymentMethod paymentMethod, Boolean isSuccess) {
        // 결제 객체 생성
        Payment payment = createPayment(member, order, order.getPaymentAmount(), paymentMethod);
        paymentRepository.save(payment);

        // 결제 시도
        if (isSuccess) {
            payment.complete();
        } else {
            payment.fail();
            orderRepository.delete(order);
            throw new IllegalStateException("결제 실패");
        }
    }
}
