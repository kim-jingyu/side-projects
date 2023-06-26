package com.photoalbum.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class AlbumResponseDto {
    private Long albumId;
    private String albumName;
    private LocalDateTime createdAt;
    private int count;
}
