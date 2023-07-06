package shoppingmall.server.dto;

import lombok.Builder;
import lombok.Data;
import shoppingmall.server.constant.ItemSellStatus;
import shoppingmall.server.entity.Item;

@Data
public class ItemResponseDto {
    private String itemName;
    private int price;
    private int stockQuantity;
    private String itemDetail;
    private ItemSellStatus itemSellStatus;

    @Builder
    public ItemResponseDto(Item item) {
        this.itemName = item.getItemName();
        this.price = item.getPrice();
        this.stockQuantity = item.getStockQuantity();
        this.itemDetail = item.getItemDetail();
        this.itemSellStatus = item.getItemSellStatus();
    }
}
