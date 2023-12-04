package com.myproject.todayhouse.item.dto.response;

import com.myproject.todayhouse.item.domain.Item;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class ItemResponse {
    private String brandName;
    private String itemName;
    private int price;
    private int stockQuantity;
    private List<ItemImgResponse> itemImgResponseList;

    @Builder
    public ItemResponse(Item item, List<ItemImgResponse> itemImgResponseList) {
        this.brandName = item.getBrandName();
        this.itemName = item.getItemName();
        this.price = item.getPrice();
        this.stockQuantity = item.getStockQuantity();
        this.itemImgResponseList = itemImgResponseList;
    }
}
