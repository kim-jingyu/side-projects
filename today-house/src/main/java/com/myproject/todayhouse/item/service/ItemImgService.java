package com.myproject.todayhouse.item.service;

import com.myproject.todayhouse.item.domain.Item;
import com.myproject.todayhouse.item.domain.ItemImg;
import com.myproject.todayhouse.item.dto.request.ItemImgRequest;
import com.myproject.todayhouse.item.dto.response.ItemImgResponse;
import com.myproject.todayhouse.item.exception.FileDeleteException;
import com.myproject.todayhouse.item.exception.FileStoreException;
import com.myproject.todayhouse.item.repository.ItemImgRepository;
import com.myproject.todayhouse.item.util.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemImgService {
    private final ItemImgRepository itemImgRepository;
    private final FileService fileService;

    @Transactional
    public ItemImgResponse saveItemImg(MultipartFile multipartFile, Item item, Boolean representYn) {
        ItemImgRequest itemImgRequest;
        try {
            itemImgRequest = fileService.storeFile(multipartFile, item.getItemId(), representYn);
        } catch (IOException e) {
            throw new FileStoreException();
        }

        ItemImg itemImg = ItemImg.createItemImg(itemImgRequest, item);
        itemImgRepository.save(itemImg);

        return ItemImgResponse.builder()
                .itemImg(itemImg)
                .build();
    }

    @Transactional
    public ItemImgResponse updateItemImg(ItemImg itemImg, MultipartFile multipartFile, Long itemId, Boolean representYn) {
        try {
            if (representYn) {
                fileService.deleteFile(itemImg.getThumbFileUrl());
            }
            fileService.deleteFile(itemImg.getStoredFileUrl());
        } catch (IOException e) {
            throw new FileDeleteException();
        }

        ItemImgRequest itemImgRequest;
        try {
            itemImgRequest = fileService.storeFile(multipartFile, itemId, representYn);
        } catch (IOException e) {
            throw new FileStoreException();
        }

        ItemImg updatedItemImg = itemImg.updateItemImg(itemImgRequest);

        return ItemImgResponse.builder()
                .itemImg(updatedItemImg)
                .build();
    }
}
