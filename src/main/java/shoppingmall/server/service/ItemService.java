package shoppingmall.server.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import shoppingmall.server.dto.ItemRequestDto;
import shoppingmall.server.dto.ItemResponseDto;
import shoppingmall.server.entity.Item;
import shoppingmall.server.entity.ItemImg;
import shoppingmall.server.repository.ItemRepository;

import java.io.IOException;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final ItemImgService itemImgService;

    public ItemResponseDto saveItem(ItemRequestDto itemRequestDto, List<MultipartFile> itemImgFileList) throws IOException {
        Item item = itemRequestDto.createItem();
        itemRepository.save(item);

        for (int i = 0; i < itemImgFileList.size(); i++) {
            if (i == 0) {
                itemImgService.saveItemImg(ItemImg.createItemImg(item, "Y"), itemImgFileList.get(i));
            } else {
                itemImgService.saveItemImg(ItemImg.createItemImg(item, "N"), itemImgFileList.get(i));
            }
        }

        return ItemResponseDto
                .builder()
                .item(item)
                .build();
    }
}
