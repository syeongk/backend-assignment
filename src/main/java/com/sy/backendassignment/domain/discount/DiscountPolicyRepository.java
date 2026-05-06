package com.sy.backendassignment.domain.discount;

import com.sy.backendassignment.domain.discount.entity.DiscountPolicy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscountPolicyRepository extends JpaRepository<DiscountPolicy, Long> {
}
