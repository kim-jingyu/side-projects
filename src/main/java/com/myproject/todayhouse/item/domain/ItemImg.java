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
    private String storedFileName;
    private String storedFileUrl;
    private String representYn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private ItemImg(String uploadFileName, String storedFileName, String storedFileUrl, String representYn) {
        this.uploadFileName = uploadFileName;
        this.storedFileName = storedFileName;
        this.storedFileUrl = storedFileUrl;
        this.representYn = representYn;
    }

    public static ItemImg createItemImg(ItemImgRequest itemImgRequest, String representYn) {
        return new ItemImg(itemImgRequest.getUploadFileName(), itemImgRequest.getStoredFileName(), itemImgRequest.getStoredFileUrl(), representYn);
    }

    public ItemImg updateItemImg(ItemImgRequest itemImgRequest) {
        this.uploadFileName = itemImgRequest.getUploadFileName();
        this.storedFileName = itemImgRequest.getStoredFileName();
        this.storedFileUrl = itemImgRequest.getStoredFileUrl();
        return this;
    }
}
