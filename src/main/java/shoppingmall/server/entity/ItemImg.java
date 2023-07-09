package shoppingmall.server.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shoppingmall.server.dto.ItemImgDto;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    private ItemImg(Item item, String uploadImageName, String storedImageName, String storedFileUrl, String representYn) {
        this.item = item;
        this.uploadImageName = uploadImageName;
        this.storedImageName = storedImageName;
        this.storedFileUrl = storedFileUrl;
        this.representYn = representYn;
    }

    public static ItemImg createItemImg(Item item, String uploadImageName, String storedImageName, String storedFileUrl, String representYn) {
        return new ItemImg(item, uploadImageName, storedImageName, storedFileUrl, representYn);
    }

    public void updateItemImg(ItemImgDto itemImgDto) {
        this.uploadImageName = itemImgDto.getUploadImageName();
        this.storedImageName = itemImgDto.getStoredImageName();
        this.storedFileUrl = itemImgDto.getStoredFileUrl();
    }

    // 연관관계 편의 메서드

}
