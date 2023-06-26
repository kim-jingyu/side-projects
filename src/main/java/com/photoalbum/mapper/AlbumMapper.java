package com.photoalbum.mapper;

import com.photoalbum.domain.Album;
import com.photoalbum.dto.AlbumResponseDto;

public class AlbumMapper {
    public static AlbumResponseDto convertToDto(Album album) {
        AlbumResponseDto dto = new AlbumResponseDto();
        dto.setAlbumId(album.getAlbumId());
        dto.setAlbumName(album.getAlbumName());
        dto.setCreatedAt(album.getCreatedAt());
        return dto;
    }
}
