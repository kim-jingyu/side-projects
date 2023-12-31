package shoppingmall.server.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import shoppingmall.server.dto.ItemImgDto;
import shoppingmall.server.entity.ItemImg;
import shoppingmall.server.file.FileService;
import shoppingmall.server.repository.ItemImgRepository;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemImgService {

    private final FileService fileService;
    private final ItemImgRepository itemImgRepository;

    public ItemImgDto saveItemImg(ItemImg itemImg, MultipartFile itemImgFile) throws IOException {
        if (itemImgFile.isEmpty()) {
            return null;
        }
        ItemImgDto itemImgDto = fileService.storeFile(itemImgFile);

        itemImg.updateItemImg(itemImgDto);
        itemImgRepository.save(itemImg);

        return itemImgDto;
    }

    public ItemImgDto updateItemImg(ItemImg itemImg, MultipartFile itemImgFile) throws IOException {
        fileService.deleteFile(itemImg.getStoredFileUrl());

        ItemImgDto itemImgDto = fileService.storeFile(itemImgFile);

        itemImg.updateItemImg(itemImgDto);

        return itemImgDto;
    }
}
