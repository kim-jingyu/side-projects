package com.myproject.todayhouse.item.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ItemRequest {
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
}
