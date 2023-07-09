package shoppingmall.server.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import shoppingmall.server.dto.ItemImgDto;
import shoppingmall.server.dto.ItemRequestDto;
import shoppingmall.server.dto.ItemResponseDto;
import shoppingmall.server.entity.Item;
import shoppingmall.server.entity.ItemImg;
import shoppingmall.server.repository.ItemImgRepository;
import shoppingmall.server.repository.ItemRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class ItemService {
    private final ItemRepository itemRepository;
    private final ItemImgService itemImgService;
    private final ItemImgRepository itemImgRepository;

    @Transactional
    public ItemResponseDto saveItem(ItemRequestDto itemRequestDto, List<MultipartFile> itemImgFileList) throws IOException {
        Item savedItem = itemRepository.save(Item.builder().requestDto(itemRequestDto).build());

        List<ItemImgDto> itemImgDtoList = new ArrayList<>();
        for (int i = 0; i < itemImgFileList.size(); i++) {
            if (i == 0) {
                itemImgDtoList.add(itemImgService.saveItemImg(ItemImg.createItemImg(savedItem, null, null, null, "Y"), itemImgFileList.get(i)));
            } else {
                itemImgDtoList.add(itemImgService.saveItemImg(ItemImg.createItemImg(savedItem, null, null, null,"N"), itemImgFileList.get(i)));
            }
        }

        return ItemResponseDto
                .builder()
                .item(savedItem)
                .itemImgDtoList(itemImgDtoList)
                .build();
    }

    public ItemResponseDto getItemDetail(Long itemId) {
        List<ItemImg> itemImgList = itemImgRepository.findByItem_ItemIdOrderByItemImgIdAsc(itemId);

        List<ItemImgDto> itemImgDtoList = new ArrayList<>();
        for (ItemImg itemImg : itemImgList) {
            itemImgDtoList.add(new ItemImgDto(itemImg));
        }

        Item findItem = itemRepository.findById(itemId)
                .orElseThrow(EntityNotFoundException::new);

        return ItemResponseDto
                .builder()
                .item(findItem)
                .itemImgDtoList(itemImgDtoList)
                .build();
    }

    @Transactional
    public ItemResponseDto updateItem(Long itemId, ItemRequestDto itemRequestDto, List<MultipartFile> itemImgFileList) throws IOException {
        Item findItem = itemRepository.findById(itemId)
                .orElseThrow(EntityNotFoundException::new);
        findItem.updateItem(itemRequestDto);

        List<ItemImgDto> itemImgDtoList = new ArrayList<>();
        List<Long> itemImgIds = itemRequestDto.getItemImgIds();
        for (int i = 0; i < itemImgIds.size(); i++) {
            ItemImg itemImg = itemImgRepository.findByItem_ItemIdAndItemImgId(findItem.getItemId(), itemImgIds.get(i))
                                    .orElseThrow(EntityNotFoundException::new);

            itemImgDtoList.add(itemImgService.updateItemImg(itemImg, itemImgFileList.get(i)));
        }

        return ItemResponseDto
                .builder()
                .item(findItem)
                .itemImgDtoList(itemImgDtoList)
                .build();
    }
}
