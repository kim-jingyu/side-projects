package com.myproject.todayhouse.item.domain;

import com.myproject.todayhouse.common.BaseTimeEntity;
import com.myproject.todayhouse.item.dto.request.ItemImgRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ItemImg extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemImgId;

    private String uploadFileName;
    private String thumbFileName;
    private String thumbFileUrl;
    private String storedFileName;
    private String storedFileUrl;
    private boolean representYn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private ItemImg(String uploadFileName, String thumbFileName, String thumbFileUrl, String storedFileName, String storedFileUrl, Boolean representYn, Item item) {
        this.uploadFileName = uploadFileName;
        this.thumbFileName = thumbFileName;
        this.thumbFileUrl = thumbFileUrl;
        this.storedFileName = storedFileName;
        this.storedFileUrl = storedFileUrl;
        this.representYn = representYn;
        this.item = item;
    }

    public static ItemImg createItemImg(ItemImgRequest itemImgRequest, Boolean representYn, Item item) {
        return new ItemImg(itemImgRequest.getUploadFileName(), itemImgRequest.getThumbFileName(), itemImgRequest.getThumbFileUrl(), itemImgRequest.getStoredFileName(), itemImgRequest.getStoredFileUrl(), representYn, item);
    }

    public ItemImg updateItemImg(ItemImgRequest itemImgRequest) {
        this.uploadFileName = itemImgRequest.getUploadFileName();
        this.thumbFileName = itemImgRequest.getThumbFileName();
        this.thumbFileUrl = itemImgRequest.getThumbFileUrl();
        this.storedFileName = itemImgRequest.getStoredFileName();
        this.storedFileUrl = itemImgRequest.getStoredFileUrl();
        this.representYn = itemImgRequest.getRepresentYn();
        return this;
    }
}
