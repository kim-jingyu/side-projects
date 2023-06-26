package com.photoalbum.service;

import com.photoalbum.domain.Album;
import com.photoalbum.domain.Photo;
import com.photoalbum.dto.AlbumResponseDto;
import com.photoalbum.repository.AlbumRepository;
import com.photoalbum.repository.PhotoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class AlbumServiceTest {
    @Autowired
    AlbumService albumService;
    @Autowired
    AlbumRepository albumRepository;
    @Autowired
    PhotoRepository photoRepository;

    @Test
    void getAlbum() {
        Album album = new Album();
        album.setAlbumName("새로운 앨범");

        Album savedAlbum = albumRepository.save(album);

        AlbumResponseDto findAlbum = albumService.getAlbum(savedAlbum.getAlbumId());
        assertThat(findAlbum.getAlbumName()).isEqualTo("새로운 앨범");
    }

    @Test
    void getAlbumByName() {
        Album album = new Album();
        album.setAlbumName("새로운 앨범");

        Album savedAlbum = albumRepository.save(album);

        AlbumResponseDto findAlbum = albumService.getAlbumByName(savedAlbum.getAlbumName());
        assertThat(findAlbum.getAlbumName()).isEqualTo("새로운 앨범");
    }

    @Test
    void testPhotoCount() {
        Album album = new Album();
        album.setAlbumName("새로운 앨범");
        Album savedAlbum = albumRepository.save(album);

        Photo photo = new Photo();
        photo.setFileName("사진1");
        photo.setAlbum(album);
        photoRepository.save(photo);

        AlbumResponseDto findAlbum = albumService.getAlbum(savedAlbum.getAlbumId());
        assertThat(findAlbum.getCount()).isEqualTo(1);
    }
}