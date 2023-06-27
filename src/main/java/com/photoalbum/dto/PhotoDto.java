package com.photoalbum.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class PhotoDto {
    private Long photoId;
    private String fileName;
    private String thumbUrl;
    private String originalUrl;
    private Long fileSize;
    private LocalDateTime uploadedAt;
    private Long albumId;
}
