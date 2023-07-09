package shoppingmall.server.entity;

import jakarta.persistence.*;
import lombok.*;
import shoppingmall.server.constant.ItemSellStatus;
import shoppingmall.server.dto.ItemRequestDto;
import shoppingmall.server.exception.OutOfStockException;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Item extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId;

    @Column(nullable = false, length = 50)
    private String itemName;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private int stockQuantity;

    @Lob
    @Column(nullable = false)
    private String itemDetail;

    @Enumerated(EnumType.STRING)
    private ItemSellStatus itemSellStatus;

    @Builder
    public Item(ItemRequestDto requestDto) {
        this.itemName = requestDto.getItemName();
        this.price = requestDto.getPrice();
        this.stockQuantity = requestDto.getStockQuantity();
        this.itemDetail = requestDto.getItemDetail();
        this.itemSellStatus = requestDto.getItemSellStatus();
    }

    public void updateItem(ItemRequestDto requestDto) {
        this.itemName = requestDto.getItemName();
        this.price = requestDto.getPrice();
        this.stockQuantity = requestDto.getStockQuantity();
        this.itemDetail = requestDto.getItemDetail();
        this.itemSellStatus = requestDto.getItemSellStatus();
    }

    // 비즈니스 메서드. 상품 주문시, 상품 재고 감소
    public void removeStock(int stockQuantity) {
        int restStock = this.stockQuantity - stockQuantity;
        if (restStock < 0) {
            throw new OutOfStockException("상품의 재고가 부족합니다.");
        }
        this.stockQuantity = restStock;
    }

    public void addStock(int stockQuantity) {
        this.stockQuantity += stockQuantity;
    }
}
