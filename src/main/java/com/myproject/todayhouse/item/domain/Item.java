package com.myproject.todayhouse.item.domain;

import com.myproject.todayhouse.common.BaseTimeEntity;
import com.myproject.todayhouse.item.dto.request.ItemCreationRequest;
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

    private Item(ItemCreationRequest itemCreationRequest) {
        this.brandName = itemCreationRequest.getBrandName();
        this.itemName = itemCreationRequest.getItemName();
        this.price = itemCreationRequest.getPrice();
        this.stockQuantity = itemCreationRequest.getStockQuantity();
        this.itemDetail = itemCreationRequest.getItemDetail();
        this.itemSellStatus = itemCreationRequest.getItemSellStatus();
    }

    public static Item createItem(ItemCreationRequest itemCreationRequest) {
        return new Item(itemCreationRequest);
    }

    public void updateItem(ItemCreationRequest itemCreationRequest) {
        this.brandName = itemCreationRequest.getBrandName();
        this.itemName = itemCreationRequest.getItemName();
        this.price = itemCreationRequest.getPrice();
        this.stockQuantity = itemCreationRequest.getStockQuantity();
        this.itemDetail = itemCreationRequest.getItemDetail();
        this.itemSellStatus = itemCreationRequest.getItemSellStatus();
    }
}
