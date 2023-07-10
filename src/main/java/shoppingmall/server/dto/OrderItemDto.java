package shoppingmall.server.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import shoppingmall.server.entity.OrderItem;

// 조회한 주문 데이터를 화면에 보냄
@Getter @Setter
public class OrderItemDto {
    private String itemName;
    private int count;
    private int orderPrice;
    private String imgUrl;

    @Builder
    public OrderItemDto(OrderItem orderItem, String imgUrl) {
        this.itemName = orderItem.getItem().getItemName();
        this.count = orderItem.getCount();
        this.orderPrice = orderItem.getOrderPrice();
        this.imgUrl = imgUrl;
    }
}
