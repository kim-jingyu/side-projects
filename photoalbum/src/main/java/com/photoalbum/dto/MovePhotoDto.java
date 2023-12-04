package com.photoalbum.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class MovePhotoDto {
    private Long toAlbumId;
    private List<Long> photoIds;
}
