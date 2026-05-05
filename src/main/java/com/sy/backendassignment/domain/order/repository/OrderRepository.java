package com.sy.backendassignment.domain.order.repository;

import com.sy.backendassignment.domain.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
