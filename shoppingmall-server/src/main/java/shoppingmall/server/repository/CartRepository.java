package shoppingmall.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shoppingmall.server.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {
    // 현재 로그인한 회원의 Cart 엔티티 조회
    Cart findByMember_MemberId(Long memberId);
}
