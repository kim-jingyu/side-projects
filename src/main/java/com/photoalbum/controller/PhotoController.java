package com.photoalbum.controller;

import com.photoalbum.dto.PhotoDto;
import com.photoalbum.service.PhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/albums/{albumId}/photos")
public class PhotoController {
    private final PhotoService photoService;

    @GetMapping(value = "/{photoId}")
    public ResponseEntity<PhotoDto> getPhotoInfo(@PathVariable Long albumId, @PathVariable Long photoId) {
        return new ResponseEntity<>(photoService.getPhoto(albumId, photoId), HttpStatus.OK);
    }
}
