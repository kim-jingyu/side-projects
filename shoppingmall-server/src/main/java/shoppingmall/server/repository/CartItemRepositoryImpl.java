package shoppingmall.server.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import shoppingmall.server.dto.CartDetailDto;
import shoppingmall.server.dto.QCartDetailDto;

import java.util.List;

import static shoppingmall.server.entity.QCartItem.*;
import static shoppingmall.server.entity.QItem.*;
import static shoppingmall.server.entity.QItemImg.*;

public class CartItemRepositoryImpl implements CartItemRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    public CartItemRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<CartDetailDto> findCartDetailDtoList(Long cartId) {
        List<CartDetailDto> content = queryFactory
                .select(new QCartDetailDto(cartItem.cartItemId, item.itemName, item.price, itemImg.storedFileUrl))
                .from(cartItem, itemImg)
                .join(cartItem.item, item)
                .where(cartItem.cart.cartId.eq(cartId),
                        itemImg.item.itemId.eq(cartItem.item.itemId),
                        itemImg.representYn.eq("Y"))
                .orderBy(cartItem.createdTime.desc())
                .fetch();

        return content;
    }
}
