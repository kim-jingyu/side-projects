package shoppingmall.server.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

// 장바구니 조회 DTO
@Getter @Setter
public class CartDetailDto {
    private Long cartItemId;
    private String itemName;
    private int price;
    private String imgUrl;

    @QueryProjection
    public CartDetailDto(Long cartItemId, String itemName, int price, String imgUrl) {
        this.cartItemId = cartItemId;
        this.itemName = itemName;
        this.price = price;
        this.imgUrl = imgUrl;
    }
}
