package shoppingmall.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shoppingmall.server.entity.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    // 장바구니에 들어갈 상품 조회
    CartItem findByCartIdAndItemId(Long cartId, Long itemId);
}
