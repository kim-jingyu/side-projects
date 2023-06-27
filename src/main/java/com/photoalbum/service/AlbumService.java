package com.photoalbum.service;

import com.photoalbum.Constants;
import com.photoalbum.domain.Album;
import com.photoalbum.domain.Photo;
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
import java.util.List;
import java.util.NoSuchElementException;
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

    public List<AlbumDto> getAlbumList(String keyword, String sort, String orderBy) {
        List<AlbumDto> albumDtos = getAlbumDtos(keyword, sort, orderBy);

        for (AlbumDto albumDto : albumDtos) {
            List<Photo> top4Photos = photoRepository.findTop4ByAlbum_AlbumIdOrderByUploadedAtDesc(albumDto.getAlbumId());
            albumDto.setThumbUrls(top4Photos.stream().map(Photo::getThumbUrl).map(thumbUrl -> Constants.PATH_PREFIX + thumbUrl).toList());
        }

        return albumDtos;
    }

    private List<AlbumDto> getAlbumDtos(String keyword, String sort, String orderBy) {
        List<AlbumDto> albumDtos;
        if (sort.equals("byName")) {
            if (orderBy.equals("desc")) {
                albumDtos = AlbumMapper.convertToDtoList(albumRepository.findByAlbumNameContainingOrderByAlbumNameDesc(keyword));
            } else if (orderBy.equals("asc")) {
                albumDtos = AlbumMapper.convertToDtoList(albumRepository.findByAlbumNameContainingOrderByAlbumNameAsc(keyword));
            } else {
                albumDtos = AlbumMapper.convertToDtoList(albumRepository.findByAlbumNameContainingOrderByAlbumNameAsc(keyword));
            }
        } else if (sort.equals("byDate")) {
            if (orderBy.equals("desc")) {
                albumDtos = AlbumMapper.convertToDtoList(albumRepository.findByAlbumNameContainingOrderByCreatedAtDesc(keyword));
            } else if (orderBy.equals("asc")) {
                albumDtos = AlbumMapper.convertToDtoList(albumRepository.findByAlbumNameContainingOrderByCreatedAtAsc(keyword));
            } else {
                albumDtos = AlbumMapper.convertToDtoList(albumRepository.findByAlbumNameContainingOrderByCreatedAtDesc(keyword));
            }
        } else {
            throw new IllegalArgumentException("알 수 없는 정렬 기준입니다.");
        }
        return albumDtos;
    }

    @Transactional
    public AlbumDto updateAlbum(Long albumId, AlbumDto albumDto) {
        Album findAlbum = albumRepository.findById(albumId)
                .orElseThrow(() -> new NoSuchElementException(String.format("앨범명 %d로 조회되는 값이 없습니다.", albumId)));

        findAlbum.setAlbumName(albumDto.getAlbumName());

        return AlbumMapper.convertToDto(findAlbum);
    }
}
