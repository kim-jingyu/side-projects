package com.myproject.todayhouse.order.repository;

import com.myproject.todayhouse.order.domain.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Orders, Long> {
}
