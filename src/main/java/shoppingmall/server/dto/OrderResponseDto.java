package shoppingmall.server.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderResponseDto {
    private Long orderId;
    private int totalPrice;

    @Builder
    public OrderResponseDto(Long orderId, int totalPrice) {
        this.orderId = orderId;
        this.totalPrice = totalPrice;
    }
}
