package com.photoalbum.mapper;

import com.photoalbum.domain.Photo;
import com.photoalbum.dto.PhotoDto;

public class PhotoMapper {
    public static PhotoDto convertToDto(Photo photo) {
        PhotoDto photoDto = new PhotoDto();
        photoDto.setPhotoId(photo.getPhotoId());
        photoDto.setFileName(photo.getFileName());
        photoDto.setThumbUrl(photo.getThumbUrl());
        photoDto.setOriginalUrl(photo.getOriginalUrl());
        photoDto.setFileSize(photo.getFileSize());
        photoDto.setUploadedAt(photo.getUploadedAt());
        photoDto.setAlbumId(photo.getAlbum().getAlbumId());
        return photoDto;
    }
}
