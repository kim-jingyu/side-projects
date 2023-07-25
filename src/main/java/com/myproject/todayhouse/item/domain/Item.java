package com.myproject.todayhouse.item.domain;

import com.myproject.todayhouse.common.BaseTimeEntity;
import com.myproject.todayhouse.item.dto.request.ItemCreateRequest;
import com.myproject.todayhouse.item.dto.request.ItemUpdateRequest;
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

    private Item(ItemCreateRequest itemCreateRequest) {
        this.brandName = itemCreateRequest.getBrandName();
        this.itemName = itemCreateRequest.getItemName();
        this.price = itemCreateRequest.getPrice();
        this.stockQuantity = itemCreateRequest.getStockQuantity();
        this.itemDetail = itemCreateRequest.getItemDetail();
        this.itemSellStatus = itemCreateRequest.getItemSellStatus();
    }

    public static Item createItem(ItemCreateRequest itemCreateRequest) {
        return new Item(itemCreateRequest);
    }

    public void updateItem(ItemUpdateRequest itemUpdateRequest) {
        this.brandName = itemUpdateRequest.getBrandName();
        this.itemName = itemUpdateRequest.getItemName();
        this.price = itemUpdateRequest.getPrice();
        this.stockQuantity = itemUpdateRequest.getStockQuantity();
        this.itemDetail = itemUpdateRequest.getItemDetail();
        this.itemSellStatus = itemUpdateRequest.getItemSellStatus();
    }
}
