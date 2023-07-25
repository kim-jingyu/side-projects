package com.myproject.todayhouse.item.util;

import com.myproject.todayhouse.item.dto.request.ItemImgRequest;
import com.myproject.todayhouse.item.dto.response.ItemImgResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class FileService {
    @Value("${itemImgLocation}")
    private String itemImgLocation;

    public ItemImgRequest storeFile(MultipartFile multipartFile, String representYn) throws IOException {
        if (multipartFile.isEmpty()) {
            return null;
        }

        String uploadFileName = multipartFile.getOriginalFilename();
        String ext = getExt(uploadFileName);

        String storedFileName = getStoredFileName(ext);
        String storedFileUrl = getStoredFileUrl(storedFileName);

        multipartFile.transferTo(new File(storedFileUrl));

        return ItemImgRequest.builder()
                .uploadFileName(uploadFileName)
                .storedFileName(storedFileName)
                .storedFileUrl(storedFileUrl)
                .representYn(representYn)
                .build();
    }

    private String getExt(String uploadFileName) {
        int pos = uploadFileName.lastIndexOf(".");
        return uploadFileName.substring(pos + 1);
    }

    private String getStoredFileName(String ext) {
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }

    private String getStoredFileUrl(String storedFileName) {
        return itemImgLocation + storedFileName;
    }
}
