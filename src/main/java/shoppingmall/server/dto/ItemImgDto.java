package shoppingmall.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ItemImgDto {
    private String uploadImageName; // 고객이 업로드한 파일 이름
    private String storedImageName;
    private String storedFileUrl;   // 서버 내부에서 관리하는 파일명
}
