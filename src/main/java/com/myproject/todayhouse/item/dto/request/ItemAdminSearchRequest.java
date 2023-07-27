package com.myproject.todayhouse.item.dto.request;

import com.myproject.todayhouse.item.domain.ItemSellStatus;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ItemAdminSearchRequest {
    // searchBy
    private String itemName;
    private String createdDate;  // 상품 등록일
    private ItemSellStatus itemSellStatus;  // 품절, 판매중
}
