package com.myproject.todayhouse.item.dto.request;

import com.myproject.todayhouse.item.domain.ItemSellStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ItemCreateRequest {
    @NotBlank(message = "브랜드명을 입력해주세요!")
    private String brandName;
    @NotBlank(message = "상품명을 입력해주세요!")
    private String itemName;

    @NotNull(message = "상품 가격을 입력해주세요!")
    private int price;
    @NotNull(message = "상품 재고를 입력해주세요!")
    private int stockQuantity;

    private String itemDetail;
    private ItemSellStatus itemSellStatus;
}
