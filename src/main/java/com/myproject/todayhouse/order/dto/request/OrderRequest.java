package com.myproject.todayhouse.order.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class OrderRequest {
    @NotNull(message = "상품 아이디를 입력해주세요!")
    private Long itemId;
    @Size(min = 1, max = 999, message = "주문 수량은 1 ~ 999개 사이만 허용됩니다!")
    private int count;
}
