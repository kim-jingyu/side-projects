package com.photoalbum.service;

import com.photoalbum.domain.Album;
import com.photoalbum.repository.AlbumRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class AlbumServiceTest {
    @Autowired
    AlbumService albumService;
    @Autowired
    AlbumRepository albumRepository;

    @Test
    void getAlbum() {
        Album album = new Album();
        album.setAlbumName("새로운 앨범");

        Album savedAlbum = albumRepository.save(album);

        Album findAlbum = albumService.getAlbum(savedAlbum.getAlbumId());
        assertThat(findAlbum.getAlbumName()).isEqualTo("새로운 앨범");
    }

    @Test
    void getAlbumByName() {
        Album album = new Album();
        album.setAlbumName("새로운 앨범");

        Album savedAlbum = albumRepository.save(album);

        Album findAlbum = albumService.getAlbumByName(savedAlbum.getAlbumName());
        assertThat(findAlbum.getAlbumName()).isEqualTo("새로운 앨범");
    }
}