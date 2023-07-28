package com.myproject.todayhouse.item.service;

import com.myproject.todayhouse.item.domain.Item;
import com.myproject.todayhouse.item.domain.ItemImg;
import com.myproject.todayhouse.item.dto.request.ItemAdminSearchRequest;
import com.myproject.todayhouse.item.dto.request.ItemCreateRequest;
import com.myproject.todayhouse.item.dto.request.ItemUpdateRequest;
import com.myproject.todayhouse.item.dto.response.ItemImgResponse;
import com.myproject.todayhouse.item.dto.response.ItemResponse;
import com.myproject.todayhouse.item.exception.ItemImgNotFoundException;
import com.myproject.todayhouse.item.exception.ItemNotFoundException;
import com.myproject.todayhouse.item.repository.ItemImgRepository;
import com.myproject.todayhouse.item.repository.ItemRepository;
import com.myproject.todayhouse.item.util.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final ItemImgService itemImgService;
    private final ItemImgRepository itemImgRepository;
    private final FileService fileService;

    @Transactional
    public ItemResponse saveItem(ItemCreateRequest itemCreateRequest, List<MultipartFile> itemImgFileList) {
        Item item = Item.createItem(itemCreateRequest);
        itemRepository.save(item);
        fileService.createItemDirectory(item);

        List<ItemImgResponse> itemImgResponseList = new ArrayList<>();
        for (int i = 0; i < itemImgFileList.size(); i++) {
            if (i == 0) {
                itemImgResponseList.add(itemImgService.saveItemImg(itemImgFileList.get(i), item, true));
            } else {
                itemImgResponseList.add(itemImgService.saveItemImg(itemImgFileList.get(i), item, false));
            }
        }

        return ItemResponse.builder()
                .item(item)
                .itemImgResponseList(itemImgResponseList)
                .build();
    }

    @Transactional
    public ItemResponse updateItem(Long itemId, ItemUpdateRequest itemUpdateRequest, List<MultipartFile> itemImgFileList) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(ItemNotFoundException::new);
        item.updateItem(itemUpdateRequest);

        List<Long> itemImgIdList = itemUpdateRequest.getItemImgIdList();
        log.info("itemImgIdList's size = {}", itemImgIdList.size());
        List<ItemImgResponse> itemImgResponseList = new ArrayList<>();
        for (int i = 0; i < itemImgIdList.size(); i++) {
            Long itemImgId = itemImgIdList.get(i);
            ItemImg itemImg = itemImgRepository.findByItem_ItemIdAndItemImgId(itemId, itemImgId)
                    .orElseThrow(() -> new ItemImgNotFoundException(itemImgId));

            itemImgResponseList.add(itemImgService.updateItemImg(itemImg, itemImgFileList.get(i), item.getItemId(), itemImg.isRepresentYn()));
        }

        return ItemResponse.builder()
                .item(item)
                .itemImgResponseList(itemImgResponseList)
                .build();
    }

    public Page<Item> getAdminItemPage(ItemAdminSearchRequest request, Pageable pageable) {
        return itemRepository.getAdminItemPage(request, pageable);
    }
}
