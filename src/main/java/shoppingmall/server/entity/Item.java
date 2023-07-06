package shoppingmall.server.entity;

import jakarta.persistence.*;
import lombok.*;
import shoppingmall.server.constant.ItemSellStatus;
import shoppingmall.server.dto.ItemRequestDto;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Item extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId;

    @Column(nullable = false, length = 50)
    private String itemName;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private int stockQuantity;

    @Lob
    @Column(nullable = false)
    private String itemDetail;

    @Enumerated(EnumType.STRING)
    private ItemSellStatus itemSellStatus;

    @OneToMany(mappedBy = "item")
    private List<ItemImg> itemImgList = new ArrayList<>();

    @Builder
    public Item(ItemRequestDto requestDto) {
        this.itemName = requestDto.getItemName();
        this.price = requestDto.getPrice();
        this.stockQuantity = requestDto.getStockQuantity();
        this.itemDetail = requestDto.getItemDetail();
        this.itemSellStatus = requestDto.getItemSellStatus();
    }
}
