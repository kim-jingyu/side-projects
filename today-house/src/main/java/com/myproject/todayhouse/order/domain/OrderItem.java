package com.myproject.todayhouse.order.domain;

import com.myproject.todayhouse.common.BaseTimeEntity;
import com.myproject.todayhouse.item.domain.Item;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class OrderItem extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemId;

    private int orderPrice;
    private int count;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Orders order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private OrderItem(Item item, int count) {
        this.orderPrice = item.getPrice();
        this.count = count;
        this.item = item;
    }

    public void setOrder(Orders order) {
        this.order = order;
    }

    public static OrderItem createOrderItem(Item item, int count) { // 만들 상품과 그 개수
        OrderItem orderItem = new OrderItem(item, count);
        item.reduceStock(count);
        return orderItem;
    }

    public int getTotalPrice() {
        return orderPrice * count;
    }

    public void cancelOrderItem() {
        item.addStock(count);
    }
}
