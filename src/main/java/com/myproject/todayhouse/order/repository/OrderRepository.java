package com.myproject.todayhouse.order.repository;

import com.myproject.todayhouse.order.domain.Orders;
import com.myproject.todayhouse.order.dto.response.OrderResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Orders, Long> {
    @Query("select new com.myproject.order.dto.response.OrderResponse(o.orderId, o.totalPrice, o.count) from Orders o join Member m where m.memberId = :memberId")
    List<OrderResponse> findOrderList(Long memberId);
}
