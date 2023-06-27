package com.photoalbum.controller;

import com.photoalbum.service.PhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/albums/{albumId}/photos")
public class PhotoController {
    private final PhotoService photoService;
}
