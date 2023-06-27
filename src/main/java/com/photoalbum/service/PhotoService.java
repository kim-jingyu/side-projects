package com.photoalbum.service;

import com.photoalbum.domain.Photo;
import com.photoalbum.dto.PhotoDto;
import com.photoalbum.mapper.PhotoMapper;
import com.photoalbum.repository.PhotoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PhotoService {
    private final PhotoRepository photoRepository;

    public PhotoDto getPhoto(Long albumId, Long photoId) {
        Photo findPhoto = photoRepository.findPhoto(albumId, photoId)
                .orElseThrow(() -> new NoSuchElementException(String.format("에러! %d번째 사진이 없습니다.", photoId)));

        return PhotoMapper.convertToDto(findPhoto);
    }
}
