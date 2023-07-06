package shoppingmall.server.entity;

import jakarta.persistence.*;
import lombok.Getter;
import shoppingmall.server.dto.ItemImgDto;

@Entity
@Getter
public class ItemImg extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemImgId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private String uploadImageName; // 고객이 업로드한 파일 이름

    private String storedImageName;
    private String storedFileUrl;        // 서버 내부에서 관리하는 파일

    private String representYn;

    public void updateItemImg(ItemImgDto itemImgDto) {
        this.uploadImageName = itemImgDto.getUploadImageName();
        this.storedImageName = itemImgDto.getStoredImageName();
        this.storedFileUrl = itemImgDto.getStoredFileUrl();
    }
}
