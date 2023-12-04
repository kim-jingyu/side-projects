package com.photoalbum.service;

import com.photoalbum.domain.Album;
import com.photoalbum.domain.Photo;
import com.photoalbum.dto.PhotoDto;
import com.photoalbum.repository.AlbumRepository;
import com.photoalbum.repository.PhotoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class PhotoServiceTest {
    @Autowired
    PhotoService photoService;
    @Autowired
    PhotoRepository photoRepository;
    @Autowired
    AlbumRepository albumRepository;

    @Test
    void testGetPhoto() {
        Album album = new Album();
        album.setAlbumName("앨범1");
        Album savedAlbum = albumRepository.save(album);

        Photo photo = new Photo();
        photo.setFileName("사진1");
        photo.setAlbum(savedAlbum);
        Photo savedPhoto = photoRepository.save(photo);

        PhotoDto findPhoto = photoService.getPhoto(savedAlbum.getAlbumId(), savedPhoto.getPhotoId());

        assertThat(findPhoto.getFileName()).isEqualTo("사진1");
        assertThat(findPhoto.getAlbumId()).isEqualTo(savedAlbum.getAlbumId());
    }
}