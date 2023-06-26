package com.photoalbum.controller;

import com.photoalbum.dto.AlbumDto;
import com.photoalbum.service.AlbumService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;

@RestController
@RequestMapping("/albums")
@RequiredArgsConstructor
@Slf4j
public class AlbumController {
    private final AlbumService albumService;

    @GetMapping(value = "/{albumId}")
    public ResponseEntity<AlbumDto> getAlbum(@PathVariable Long albumId) {
        AlbumDto album = albumService.getAlbum(albumId);
        return new ResponseEntity<>(album, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<AlbumDto> createAlbum(@RequestBody AlbumDto albumDto) throws IOException {
        AlbumDto savedAlbumDto = albumService.createAlbum(albumDto);
        return new ResponseEntity<>(savedAlbumDto, HttpStatus.OK);
    }
}
