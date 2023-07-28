package com.myproject.todayhouse.item.dto.request;

import com.myproject.todayhouse.item.domain.ItemSellStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ItemUpdateRequest {
    private String brandName;
    private String itemName;
    private int price;
    private int stockQuantity;
    private String itemDetail;
    private ItemSellStatus itemSellStatus;
    private List<Long> itemImgIdList = new ArrayList<>();
}
