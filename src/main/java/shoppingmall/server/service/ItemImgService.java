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

    public void saveItemImg(ItemImg itemImg, MultipartFile itemImgFile) throws IOException {
        if (itemImgFile.isEmpty()) {
            return;
        }
        ItemImgDto itemImgDto = fileService.storeFile(itemImgFile);

        itemImg.updateItemImg(itemImgDto);
        itemImgRepository.save(itemImg);
    }
}
