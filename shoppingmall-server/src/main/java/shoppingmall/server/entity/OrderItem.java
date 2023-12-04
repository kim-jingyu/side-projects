package shoppingmall.server.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Orders order;

    private int count;
    private int orderPrice;

    private OrderItem(Item item, int count) {
        this.item = item;
        this.count = count;
        this.orderPrice = item.getPrice();
    }

    protected void setOrder(Orders order) {
        this.order = order;
    }

    public static OrderItem createOrderItem(Item item, int count) {
        OrderItem orderItem = new OrderItem(item, count);   // 주문할 상품과 수량

        item.removeStock(count);
        return orderItem;
    }

    // 비즈니스 로직

    // 주문한 상품 전체 가격 조회
    public int getTotalPrice() {
        return orderPrice * count;
    }

    // 주문 취소
    public void cancelOrder() {
        item.addStock(count);
    }
}
