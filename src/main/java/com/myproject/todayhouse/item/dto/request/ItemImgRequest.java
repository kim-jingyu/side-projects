package com.myproject.todayhouse.item.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ItemImgRequest {
    private String uploadFileName;
    private String storedFileName;
    private String storedFileUrl;

    @Builder
    public ItemImgRequest(String uploadFileName, String storedFileName, String storedFileUrl) {
        this.uploadFileName = uploadFileName;
        this.storedFileName = storedFileName;
        this.storedFileUrl = storedFileUrl;
    }
}
