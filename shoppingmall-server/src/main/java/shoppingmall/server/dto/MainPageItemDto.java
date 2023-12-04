package shoppingmall.server.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MainPageItemDto {
    private Long itemId;
    private String itemName;
    private String itemDetail;
    private String imgUrl;
    private Integer price;

    @QueryProjection
    public MainPageItemDto(Long itemId, String itemName, String itemDetail, String imgUrl, Integer price) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemDetail = itemDetail;
        this.imgUrl = imgUrl;
        this.price = price;
    }
}
