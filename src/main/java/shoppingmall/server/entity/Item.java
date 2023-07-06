package shoppingmall.server.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;
import shoppingmall.server.constant.ItemSellStatus;

import java.time.LocalDateTime;

@Entity
@Getter
@ToString
public class Item {
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

    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
