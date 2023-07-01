package com.photoalbum.controller;

import com.photoalbum.dto.PhotoDto;
import com.photoalbum.service.PhotoService;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RestController
@RequiredArgsConstructor
@RequestMapping("/albums/{albumId}/photos")
@Slf4j
public class PhotoController {
    private final PhotoService photoService;

    @GetMapping(value = "/{photoId}")
    public ResponseEntity<PhotoDto> getPhotoInfo(@PathVariable Long albumId, @PathVariable Long photoId) {
        return new ResponseEntity<>(photoService.getPhoto(albumId, photoId), HttpStatus.OK);
    }

    // 사진 업로드 API
    @PostMapping
    public ResponseEntity<List<PhotoDto>> uploadPhotos(@PathVariable Long albumId, @RequestParam("photos") MultipartFile[] files) throws IOException {
        List<PhotoDto> photos = new ArrayList<>();
        for (MultipartFile file : files) {
            String ext = StringUtils.getFilenameExtension(file.getOriginalFilename());
            if (!isSupportedExtension(ext) || !isImage(file)) {
                return ResponseEntity
                        .badRequest()
                        .build();
            }

            PhotoDto photoDto = photoService.savePhoto(file, albumId);
            photos.add(photoDto);
        }
        return new ResponseEntity<>(photos, HttpStatus.OK);
    }

    private boolean isImage(MultipartFile file) throws IOException {
        boolean result = false;
        BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
        if (bufferedImage != null) {
            result = true;
        }
        return result;
    }

    private boolean isSupportedExtension(String ext) {
        return ext != null && (
                ext.equals("png") || ext.equals("jpg") || ext.equals("jpeg") || ext.equals("pdf")
                );
    }

    @GetMapping(value = "/download")
    public void downloadPhotos(@RequestParam("photoIds") Long[] photoIds, HttpServletResponse response) {
        try {
            if (photoIds.length == 1) {
                File file = photoService.getImageFile(photoIds[0]);
                OutputStream outputStream = response.getOutputStream();
                IOUtils.copy(new FileInputStream(file), outputStream);
                outputStream.close();
            } else {
                // zip 파일 다운로드
                response.setContentType("application/zip");
                response.setHeader("Content-Disposition", "attachment; filename=\"zipFiles.zip\"");

                List<File> files = new LinkedList<>();

                for (Long photoId : photoIds) {
                    files.add(photoService.getImageFile(photoId));
                }

                ZipOutputStream zipOutputStream = new ZipOutputStream(response.getOutputStream());

                for (File file : files) {
                    FileSystemResource fileSystemResource = new FileSystemResource(file);
                    ZipEntry zipEntry = new ZipEntry(fileSystemResource.getFilename());

                    zipOutputStream.putNextEntry(zipEntry);

                    StreamUtils.copy(fileSystemResource.getInputStream(), zipOutputStream);
                    zipOutputStream.closeEntry();
                }

                zipOutputStream.finish();
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Error");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
