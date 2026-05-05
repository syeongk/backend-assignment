package com.sy.backendassignment.domain.order;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PaymentStatus {
    READY("결제 준비"),
    COMPLETED("결제 완료"),
    FAILED("결제 실패");

    private final String description;
}
