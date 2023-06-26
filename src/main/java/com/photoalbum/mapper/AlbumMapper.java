package com.photoalbum.mapper;

import com.photoalbum.domain.Album;
import com.photoalbum.dto.AlbumDto;

public class AlbumMapper {
    public static AlbumDto convertToDto(Album album) {
        AlbumDto dto = new AlbumDto();
        dto.setAlbumId(album.getAlbumId());
        dto.setAlbumName(album.getAlbumName());
        dto.setCreatedAt(album.getCreatedAt());
        return dto;
    }

    public static Album convertToModel(AlbumDto albumDto) {
        Album album = new Album();
        album.setAlbumId(albumDto.getAlbumId());
        album.setAlbumName(albumDto.getAlbumName());
        album.setCreatedAt(albumDto.getCreatedAt());
        return album;
    }
}
