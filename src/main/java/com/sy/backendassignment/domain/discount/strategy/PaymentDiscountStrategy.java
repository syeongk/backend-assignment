package com.sy.backendassignment.domain.discount.strategy;

import com.sy.backendassignment.domain.discount.DiscountRequest;
import com.sy.backendassignment.domain.discount.entity.AppliedDiscount;
import com.sy.backendassignment.domain.discount.repository.DiscountPolicyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.sy.backendassignment.domain.discount.entity.AppliedDiscount.createAppliedDiscount;

@Component
@RequiredArgsConstructor
public class PaymentDiscountStrategy implements DiscountStrategy {
    private final DiscountPolicyRepository discountPolicyRepository;

    @Override
    public AppliedDiscount applyDiscount(DiscountRequest request) {
        // 현재 결제 수단에 맞는 정책이 있는지 조회
        return discountPolicyRepository.findValidPolicy(request.paymentMethod(), LocalDateTime.now())
                .map(policy -> {
                    // 정책이 존재하면 할인 금액 계산
                    BigDecimal discountAmount = policy.calculateDiscountAmount(request.originalPrice());

                    // 적용된 할인 객체 생성하여 반환
                    return createAppliedDiscount(request.member(), policy, discountAmount);
                })
                // 정책이 없으면(카드 결제 등 할인이 없는 경우) null 반환
                .orElse(null);
    }
}
