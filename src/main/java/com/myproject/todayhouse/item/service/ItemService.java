package com.myproject.todayhouse.item.service;

import com.myproject.todayhouse.item.domain.Item;
import com.myproject.todayhouse.item.dto.request.ItemCreationRequest;
import com.myproject.todayhouse.item.dto.response.ItemImgResponse;
import com.myproject.todayhouse.item.dto.response.ItemResponse;
import com.myproject.todayhouse.item.exception.ItemNotFoundException;
import com.myproject.todayhouse.item.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final ItemImgService itemImgService;

    @Transactional
    public ItemResponse saveItem(ItemCreationRequest itemCreationRequest, List<MultipartFile> itemImgFileList) throws IOException {
        Item item = Item.createItem(itemCreationRequest);

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
}
