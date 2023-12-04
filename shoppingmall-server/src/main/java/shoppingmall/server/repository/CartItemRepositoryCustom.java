package shoppingmall.server.repository;

import shoppingmall.server.dto.CartDetailDto;

import java.util.List;

public interface CartItemRepositoryCustom {
    List<CartDetailDto> findCartDetailDtoList(Long cartId);
}
