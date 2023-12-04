package shoppingmall.server.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/** 장바구니 페이지에서 주문할 상품 데이터
 * 장바구니의 여러 개의 상품을 하나의 주문에 담을 수 있다.
 * 주문한 상품은 장바구니에서 삭제해야 한다.
 */
@Getter @Setter
public class CartOrderDto {
    private Long cartItemId;
    private List<CartOrderDto> cartOrderDtoList;
}
