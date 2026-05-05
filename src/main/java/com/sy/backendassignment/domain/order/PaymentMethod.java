package com.sy.backendassignment.domain.order;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PaymentMethod {
    CREDIT_CARD("신용카드"),
    POINT("포인트");

    private final String description;
}
