package com.photoalbum.controller;

import com.photoalbum.dto.PhotoDto;
import com.photoalbum.service.PhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/albums/{albumId}/photos")
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
            PhotoDto photoDto = photoService.savePhoto(file, albumId);
            photos.add(photoDto);
        }
        return new ResponseEntity<>(photos, HttpStatus.OK);
    }
}
