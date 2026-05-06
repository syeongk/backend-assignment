package com.sy.backendassignment.domain.discount.repository;

import com.sy.backendassignment.domain.discount.entity.DiscountPolicy;
import com.sy.backendassignment.domain.order.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface DiscountPolicyRepository extends JpaRepository<DiscountPolicy, Long> {
    @Query("SELECT dp FROM DiscountPolicy dp " +
            "WHERE dp.discountType = :method AND dp.startAt <= :now")
    Optional<DiscountPolicy> findValidPolicy(PaymentMethod method, LocalDateTime now);
}
