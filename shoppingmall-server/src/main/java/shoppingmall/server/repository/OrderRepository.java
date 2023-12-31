package shoppingmall.server.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import shoppingmall.server.entity.Orders;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Orders, Long> {
    // 현재 로그인한 사용자의 주문 데이터를 페이징 조건에 맞춰서 조회
    List<Orders> findByMember_EmailOrderByOrderDateDesc(@Param("email") String email, Pageable pageable);

    // 현재 로그인한 회원의 주문 개수가 몇 개인지 조회
    Long countByMember_Email(@Param("email") String email);

    Optional<Orders> findByMember_EmailAndOrderId(String email, Long orderId);
}
