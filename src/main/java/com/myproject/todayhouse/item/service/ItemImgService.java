package com.myproject.todayhouse.item.service;

import com.myproject.todayhouse.item.domain.ItemImg;
import com.myproject.todayhouse.item.dto.request.ItemImgRequest;
import com.myproject.todayhouse.item.dto.response.ItemImgResponse;
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
    private ItemImgRepository itemImgRepository;
    private FileService fileService;

    @Transactional
    public ItemImgResponse saveItemImg(MultipartFile multipartFile, String representYn) throws IOException {
        ItemImgRequest itemImgRequest = fileService.storeFile(multipartFile, representYn);

        ItemImg itemImg = ItemImg.createItemImg(itemImgRequest);
        itemImgRepository.save(itemImg);

        return ItemImgResponse.builder()
                .itemImg(itemImg)
                .build();
    }
}
