package com.sy.backendassignment.domain.discount;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DiscountUnit {
    WON("원"),
    PERCENT("%");

    private final String description;
}
