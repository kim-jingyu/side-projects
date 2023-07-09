package shoppingmall.server.dto;

import lombok.Builder;
import lombok.Data;
import shoppingmall.server.constant.ItemSellStatus;
import shoppingmall.server.entity.Item;

import java.util.List;

@Data
public class ItemResponseDto {
    private Long itemId;
    private String itemName;
    private int price;
    private int stockQuantity;
    private String itemDetail;
    private ItemSellStatus itemSellStatus;
    private List<ItemImgDto> itemImgDtoList;

    @Builder
    public ItemResponseDto(Item item, List<ItemImgDto> itemImgDtoList) {
        this.itemId = item.getItemId();
        this.itemName = item.getItemName();
        this.price = item.getPrice();
        this.stockQuantity = item.getStockQuantity();
        this.itemDetail = item.getItemDetail();
        this.itemSellStatus = item.getItemSellStatus();
        this.itemImgDtoList = itemImgDtoList;
    }
}
