package shoppingmall.server.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import shoppingmall.server.constant.OrderStatus;
import shoppingmall.server.entity.Orders;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

// 주문 정보를 담을 DTO
@Getter @Setter
public class OrderHistoryDto {
    private Long orderId;
    private String orderDate;
    private OrderStatus orderStatus;
    private List<OrderItemDto> orderItemDtoList = new ArrayList<>();    // 주문 상품 리스트

    @Builder
    public OrderHistoryDto(Orders order) {
        this.orderId = order.getOrderId();
        this.orderDate = order.getOrderDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.orderStatus = order.getOrderStatus();
    }

    public void addOrderItemDto(OrderItemDto orderItemDto) {
        orderItemDtoList.add(orderItemDto);
    }
}
