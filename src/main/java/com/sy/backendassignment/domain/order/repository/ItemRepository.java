package com.sy.backendassignment.domain.order.repository;

import com.sy.backendassignment.domain.order.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
