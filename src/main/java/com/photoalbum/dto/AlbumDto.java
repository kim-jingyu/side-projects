package com.photoalbum.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
public class AlbumDto {
    private Long albumId;
    private String albumName;
    private LocalDateTime createdAt;
    private int count;
    private List<String> thumbUrls;
}
