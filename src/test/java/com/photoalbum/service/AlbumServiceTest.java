package com.photoalbum.service;

import com.photoalbum.Constants;
import com.photoalbum.domain.Album;
import com.photoalbum.domain.Photo;
import com.photoalbum.dto.AlbumDto;
import com.photoalbum.repository.AlbumRepository;
import com.photoalbum.repository.PhotoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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

        AlbumDto findAlbum = albumService.getAlbum(savedAlbum.getAlbumId());
        assertThat(findAlbum.getAlbumName()).isEqualTo("새로운 앨범");
    }

    @Test
    void getAlbumByName() {
        Album album = new Album();
        album.setAlbumName("새로운 앨범");

        Album savedAlbum = albumRepository.save(album);

        AlbumDto findAlbum = albumService.getAlbumByName(savedAlbum.getAlbumName());
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

        AlbumDto findAlbum = albumService.getAlbum(savedAlbum.getAlbumId());
        assertThat(findAlbum.getCount()).isEqualTo(1);
    }

    @Test
    void testCreateAlbum() throws IOException {
        AlbumDto newAlbumDto = new AlbumDto();
        newAlbumDto.setAlbumName("새로운 앨범");

        AlbumDto savedAlbumDto = albumService.createAlbum(newAlbumDto);

        assertThat(savedAlbumDto.getAlbumName()).isEqualTo("새로운 앨범");

        Files.deleteIfExists(Paths.get(Constants.PATH_PREFIX + "/photos/original/" + savedAlbumDto.getAlbumId()));
        Files.deleteIfExists(Paths.get(Constants.PATH_PREFIX + "/photos/thumb/" + savedAlbumDto.getAlbumId()));
    }
}