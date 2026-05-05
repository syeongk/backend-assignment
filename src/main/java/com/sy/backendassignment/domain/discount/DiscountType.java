package com.sy.backendassignment.domain.discount;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DiscountType {
    POINT("포인트 할인"),
    GRADE("등급 할인");

    private final String description;
}
