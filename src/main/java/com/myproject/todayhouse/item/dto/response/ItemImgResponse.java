package com.myproject.todayhouse.item.dto.response;

import com.myproject.todayhouse.item.domain.ItemImg;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ItemImgResponse {
    private Long itemImgId;
    private String uploadFileName;
    private String storedFileName;
    private String storedFileUrl;
    private String representYn;

    @Builder
    public ItemImgResponse(ItemImg itemImg) {
        this.itemImgId = itemImg.getItemImgId();
        this.uploadFileName = itemImg.getUploadFileName();
        this.storedFileName = itemImg.getStoredFileName();
        this.storedFileUrl = itemImg.getStoredFileUrl();
        this.representYn = itemImg.getRepresentYn();
    }
}
