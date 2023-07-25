package com.myproject.todayhouse.item.util;

import com.myproject.todayhouse.item.dto.request.ItemImgRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

public class FileService {
    @Value("${itemImgLocation}")
    private String itemImgLocation;

    public ItemImgRequest storeFile(MultipartFile multipartFile) throws IOException {
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
                .build();
    }

    public void deleteFile(String filePath) throws IOException {
        Files.delete(Path.of(filePath));
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
