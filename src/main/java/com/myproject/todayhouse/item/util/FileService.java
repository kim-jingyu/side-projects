package com.myproject.todayhouse.item.util;

import com.myproject.todayhouse.item.domain.Item;
import com.myproject.todayhouse.item.dto.request.ItemImgRequest;
import com.myproject.todayhouse.item.exception.CreateDirectoryException;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Component
public class FileService {
    @Value("${originalItemImgLocation}")
    private String originalItemImgLocation;
    @Value("${thumbItemImgLocation}")
    private String thumbItemImgLocation;
    @Value("${thumbSize}")
    private int thumbSize;

    public ItemImgRequest storeFile(MultipartFile multipartFile, Long itemId, Boolean representYn) throws IOException {
        if (multipartFile.isEmpty()) {
            return null;
        }

        String uploadFileName = multipartFile.getOriginalFilename();
        String ext = StringUtils.getFilenameExtension(uploadFileName);

        String storedFileName = getStoredFileName(ext);
        String storedFileUrl = getStoredFileUrl(storedFileName, itemId);
        Files.copy(multipartFile.getInputStream(), Paths.get(storedFileUrl));

        String thumbFileName = null;
        String thumbFileUrl = null;
        if (representYn) {
            thumbFileName = storedFileName;
            BufferedImage thumbImg = Scalr.resize(ImageIO.read(multipartFile.getInputStream()), thumbSize, thumbSize);
            thumbFileUrl = getThumbFileUrl(storedFileName, itemId);
            File thumbFile = new File(thumbFileUrl);
            ImageIO.write(thumbImg, ext, thumbFile);
        }

        return ItemImgRequest.builder()
                .uploadFileName(uploadFileName)
                .thumbFileName(thumbFileName)
                .thumbFileUrl(thumbFileUrl)
                .storedFileName(storedFileName)
                .storedFileUrl(storedFileUrl)
                .representYn(representYn)
                .build();
    }

    public void deleteFile(String filePath) throws IOException {
        Files.delete(Path.of(filePath));
    }

    public void createItemDirectory(Item item) {
        try {
            Files.createDirectories(Paths.get(originalItemImgLocation + item.getItemId()));
            Files.createDirectories(Paths.get(thumbItemImgLocation + item.getItemId()));
        } catch (IOException e) {
            throw new CreateDirectoryException();
        }
    }

    private String getStoredFileName(String ext) {
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }

    private String getStoredFileUrl(String storedFileName, Long itemId) {
        return originalItemImgLocation + itemId + "/" + storedFileName;
    }

    private String getThumbFileUrl(String storedFileName, Long itemId) {
        return thumbItemImgLocation + itemId + "/" + storedFileName;
    }
}