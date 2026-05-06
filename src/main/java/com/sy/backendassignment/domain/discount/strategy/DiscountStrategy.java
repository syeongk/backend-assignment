package com.sy.backendassignment.domain.discount.strategy;

import com.sy.backendassignment.domain.discount.DiscountRequest;
import com.sy.backendassignment.domain.discount.entity.AppliedDiscount;

public interface DiscountStrategy {
    AppliedDiscount applyDiscount(DiscountRequest request);
}
