package com.sy.backendassignment.domain.discount.handler;

import com.sy.backendassignment.domain.discount.DiscountRequest;
import com.sy.backendassignment.domain.discount.entity.AppliedDiscount;

public interface DiscountHandler {
    AppliedDiscount applyDiscount(DiscountRequest request);
}
