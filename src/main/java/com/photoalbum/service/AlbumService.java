package com.photoalbum.service;

import com.photoalbum.Constants;
import com.photoalbum.domain.Album;
import com.photoalbum.dto.AlbumDto;
import com.photoalbum.mapper.AlbumMapper;
import com.photoalbum.repository.AlbumRepository;
import com.photoalbum.repository.PhotoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AlbumService {
    private final AlbumRepository albumRepository;
    private final PhotoRepository photoRepository;

    public AlbumDto getAlbum(Long albumId) {
        Optional<Album> album = albumRepository.findById(albumId);

        if (album.isPresent()) {
            AlbumDto dto = AlbumMapper.convertToDto(album.get());
            dto.setCount(photoRepository.countByAlbum_AlbumId(albumId));
            return dto;
        } else {
            throw new EntityNotFoundException(String.format("앨범 아이디 %d로 조회되는 값이 없습니다.", albumId));
        }
    }

    public AlbumDto getAlbumByName(String albumName) {
        Optional<Album> album = albumRepository.findByAlbumName(albumName);

        if (album.isPresent()) {
            AlbumDto dto = AlbumMapper.convertToDto(album.get());
            dto.setCount(photoRepository.countByAlbum_AlbumId(album.get().getAlbumId()));
            return dto;
        } else {
            throw new EntityNotFoundException(String.format("앨범명 %d으로 조회되는 값이 없습니다.", albumName));
        }
    }

    @Transactional
    public AlbumDto createAlbum(AlbumDto albumDto) throws IOException {
        Album album = AlbumMapper.convertToModel(albumDto);
        albumRepository.save(album);
        createAlbumDirectories(album);
        return AlbumMapper.convertToDto(album);
    }

    /**
     * 앨범 디렉토리 생성하기
     * 파일과 경로 관련된 작업을 구현하기 위한 라이브러리 Paths, Files
     * Paths.get 메서드 내 디렉토리 경로를 넣어줌으로써 Paths 객체를 만든다.
     * Files.createDirectories 메서드 내 Paths 객체를 입력하면 폴더가 생성된다.
     */
    private void createAlbumDirectories(Album album) throws IOException {
        Files.createDirectories(Paths.get(Constants.PATH_PREFIX + "/photos/original/" + album.getAlbumId()));
        Files.createDirectories(Paths.get(Constants.PATH_PREFIX + "/photos/thumb/" + album.getAlbumId()));
    }
}
