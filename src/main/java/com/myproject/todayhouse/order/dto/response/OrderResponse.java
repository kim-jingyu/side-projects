package com.myproject.todayhouse.order.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderResponse {
    private Long orderId;
    private int totalPrice;
    private int count;

    @Builder
    public OrderResponse(Long orderId, int totalPrice, int count) {
        this.orderId = orderId;
        this.totalPrice = totalPrice;
        this.count = count;
    }
}
