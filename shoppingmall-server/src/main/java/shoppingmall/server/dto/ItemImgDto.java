package shoppingmall.server.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import shoppingmall.server.entity.ItemImg;

@Data
@AllArgsConstructor
public class ItemImgDto {
    private Long itemImgId;
    private String uploadImageName; // 고객이 업로드한 파일 이름
    private String storedImageName;
    private String storedFileUrl;   // 서버 내부에서 관리하는 파일명
    private String representYn;

    @Builder
    public ItemImgDto(ItemImg itemImg) {
        this.itemImgId = itemImg.getItemImgId();
        this.uploadImageName = itemImg.getUploadImageName();
        this.storedImageName = itemImg.getStoredImageName();
        this.storedFileUrl = itemImg.getStoredFileUrl();
        this.representYn = itemImg.getRepresentYn();
    }
}
