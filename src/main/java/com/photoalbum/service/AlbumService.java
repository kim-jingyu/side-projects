package com.photoalbum.service;

import com.photoalbum.domain.Album;
import com.photoalbum.dto.AlbumResponseDto;
import com.photoalbum.mapper.AlbumMapper;
import com.photoalbum.repository.AlbumRepository;
import com.photoalbum.repository.PhotoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AlbumService {
    private final AlbumRepository albumRepository;
    private final PhotoRepository photoRepository;

    public AlbumResponseDto getAlbum(Long albumId) {
        Optional<Album> album = albumRepository.findById(albumId);

        if (album.isPresent()) {
            AlbumResponseDto dto = AlbumMapper.convertToDto(album.get());
            dto.setCount(photoRepository.countByAlbum_AlbumId(albumId));
            return dto;
        } else {
            throw new EntityNotFoundException(String.format("앨범 아이디 %d로 조회되는 값이 없습니다.", albumId));
        }
    }

    public AlbumResponseDto getAlbumByName(String albumName) {
        Optional<Album> album = albumRepository.findByName(albumName);

        if (album.isPresent()) {
            AlbumResponseDto dto = AlbumMapper.convertToDto(album.get());
            dto.setCount(photoRepository.countByAlbum_AlbumId(album.get().getAlbumId()));
            return dto;
        } else {
            throw new EntityNotFoundException(String.format("앨범명 %d으로 조회되는 값이 없습니다.", albumName));
        }
    }
}
