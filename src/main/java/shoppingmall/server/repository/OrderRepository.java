package shoppingmall.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shoppingmall.server.entity.Orders;

public interface OrderRepository extends JpaRepository<Orders, Long> {
}
