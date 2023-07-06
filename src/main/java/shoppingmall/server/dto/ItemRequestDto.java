package shoppingmall.server.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;
import org.springframework.web.multipart.MultipartFile;
import shoppingmall.server.constant.ItemSellStatus;
import shoppingmall.server.entity.Item;

import java.util.List;

@Getter @Setter
@ToString
public class ItemRequestDto {
    @NotBlank(message = "상품명은 필수 입력 값입니다.")
    private String itemName;

    @NotNull(message = "가격은 필수 입력 값입니다.")
    private int price;

    @NotNull(message = "재고는 필수 입력 값입니다.")
    private int stockQuantity;

    @NotBlank(message = "상세 설명란은 필수 입력 값입니다.")
    private String itemDetail;

    private ItemSellStatus itemSellStatus;

    private List<MultipartFile> imageFiles;

}
