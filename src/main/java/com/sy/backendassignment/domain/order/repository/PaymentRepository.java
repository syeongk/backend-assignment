package com.sy.backendassignment.domain.order.repository;

import com.sy.backendassignment.domain.order.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
