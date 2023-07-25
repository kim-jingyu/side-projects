package com.myproject.todayhouse.item.domain;

import com.myproject.todayhouse.common.BaseTimeEntity;
import com.myproject.todayhouse.item.dto.request.ItemRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Item extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId;

    @Column(nullable = false)
    private String brandName;
    @Column(nullable = false)
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

    private Item(ItemRequest itemRequest) {
        this.brandName = itemRequest.getBrandName();
        this.itemName = itemRequest.getItemName();
        this.price = itemRequest.getPrice();
        this.stockQuantity = itemRequest.getStockQuantity();
        this.itemDetail = itemRequest.getItemDetail();
        this.itemSellStatus = itemRequest.getItemSellStatus();
    }

    public static Item createItem(ItemRequest itemRequest) {
        return new Item(itemRequest);
    }

    public void updateItem(ItemRequest itemRequest) {
        this.price = itemRequest.getPrice();
        this.stockQuantity = itemRequest.getStockQuantity();
        this.itemDetail = itemRequest.getItemDetail();
    }
}
