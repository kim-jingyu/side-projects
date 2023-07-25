package com.myproject.todayhouse.item.dto.request;

import com.myproject.todayhouse.item.domain.ItemSellStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ItemCreationRequest {
    @NotBlank
    private String brandName;
    @NotBlank
    private String itemName;
    @NotBlank
    private int price;
    @NotBlank
    private int stockQuantity;
    @NotBlank
    private String itemDetail;
    @NotBlank
    private ItemSellStatus itemSellStatus;
}
