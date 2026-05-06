package com.sy.backendassignment.domain.order.service;

import com.sy.backendassignment.domain.member.entity.Member;
import com.sy.backendassignment.domain.order.PaymentMethod;
import com.sy.backendassignment.domain.order.entity.Order;
import com.sy.backendassignment.domain.order.entity.Payment;
import com.sy.backendassignment.domain.order.repository.OrderRepository;
import com.sy.backendassignment.domain.order.repository.PaymentRepository;
import com.sy.backendassignment.exception.BusinessException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.sy.backendassignment.domain.order.entity.Payment.createPayment;
import static com.sy.backendassignment.exception.ErrorCode.PAYMENT_FAILED;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;

    @Transactional
    public void pay(Member member, Order order, PaymentMethod paymentMethod, Boolean isSuccess) {
        // 결제 객체 생성
        Payment payment = createPayment(member, order, paymentMethod);
        paymentRepository.save(payment);

        // 결제 시도
        if (isSuccess) {
            payment.complete();
        } else {
            payment.fail();
            orderRepository.delete(order);
            throw new BusinessException(PAYMENT_FAILED);
        }
    }
}
