package com.myproject.todayhouse.item.service;

import com.myproject.todayhouse.item.domain.Item;
import com.myproject.todayhouse.item.domain.ItemImg;
import com.myproject.todayhouse.item.dto.request.ItemCreateRequest;
import com.myproject.todayhouse.item.dto.request.ItemUpdateRequest;
import com.myproject.todayhouse.item.dto.response.ItemImgResponse;
import com.myproject.todayhouse.item.dto.response.ItemResponse;
import com.myproject.todayhouse.item.exception.ItemImgNotFoundException;
import com.myproject.todayhouse.item.exception.ItemNotFoundException;
import com.myproject.todayhouse.item.repository.ItemImgRepository;
import com.myproject.todayhouse.item.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final ItemImgService itemImgService;
    private final ItemImgRepository itemImgRepository;

    @Transactional
    public ItemResponse saveItem(ItemCreateRequest itemCreateRequest, List<MultipartFile> itemImgFileList) {
        Item item = Item.createItem(itemCreateRequest);

        List<ItemImgResponse> itemImgResponseList = new ArrayList<>();
        for (int i = 0; i < itemImgFileList.size(); i++) {
            if (i == 0) {
                itemImgResponseList.add(itemImgService.saveItemImg(itemImgFileList.get(i), "Y"));
            } else {
                itemImgResponseList.add(itemImgService.saveItemImg(itemImgFileList.get(i), "N"));
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
        List<ItemImgResponse> itemImgResponseList = new ArrayList<>();
        for (int i = 0; i < itemImgIdList.size(); i++) {
            Long itemImgId = itemImgIdList.get(i);
            ItemImg itemImg = itemImgRepository.findByItem_ItemIdAndItemImgId(itemId, itemImgId)
                    .orElseThrow(() -> new ItemImgNotFoundException(itemImgId));

            itemImgResponseList.add(itemImgService.updateItemImg(itemImg, itemImgFileList.get(i)));
        }

        return ItemResponse.builder()
                .item(item)
                .itemImgResponseList(itemImgResponseList)
                .build();
    }
}
