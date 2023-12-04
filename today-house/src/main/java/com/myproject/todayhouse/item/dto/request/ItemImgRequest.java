package com.myproject.todayhouse.item.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ItemImgRequest {
    private String uploadFileName;
    private String thumbFileName;
    private String thumbFileUrl;
    private String storedFileName;
    private String storedFileUrl;
    private Boolean representYn;

    @Builder
    public ItemImgRequest(String uploadFileName, String thumbFileName, String thumbFileUrl, String storedFileName, String storedFileUrl, Boolean representYn) {
        this.uploadFileName = uploadFileName;
        this.thumbFileName = thumbFileName;
        this.thumbFileUrl = thumbFileUrl;
        this.storedFileName = storedFileName;
        this.storedFileUrl = storedFileUrl;
        this.representYn = representYn;
    }
}
