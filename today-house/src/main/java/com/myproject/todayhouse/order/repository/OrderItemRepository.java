package com.myproject.todayhouse.order.repository;

import com.myproject.todayhouse.order.domain.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
