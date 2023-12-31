package com.photoalbum.service;

import com.photoalbum.Constants;
import com.photoalbum.domain.Album;
import com.photoalbum.domain.Photo;
import com.photoalbum.dto.PhotoDto;
import com.photoalbum.mapper.PhotoMapper;
import com.photoalbum.repository.AlbumRepository;
import com.photoalbum.repository.PhotoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.imgscalr.Scalr;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PhotoService {
    private final PhotoRepository photoRepository;
    private final AlbumRepository albumRepository;

    public PhotoDto getPhoto(Long albumId, Long photoId) {
        Photo findPhoto = photoRepository.findPhoto(albumId, photoId)
                .orElseThrow(() -> new NoSuchElementException(String.format("에러! %d번째 사진이 없습니다.", photoId)));

        return PhotoMapper.convertToDto(findPhoto);
    }

    public List<PhotoDto> getPhotoList(Long albumId) {
        List<PhotoDto> photoDtos = new ArrayList<>();
        for (Photo photo : photoRepository.findPhotoList(albumId)) {
            photoDtos.add(PhotoMapper.convertToDto(photo));
        }

        return photoDtos;
    }
    @Transactional
    public PhotoDto savePhoto(MultipartFile file, Long albumId) throws IOException {
        Album album = albumRepository.findById(albumId)
                .orElseThrow(() -> new EntityNotFoundException("앨범이 존재하지 않습니다."));

        String filename = file.getOriginalFilename();
        long fileSize = file.getSize();

        filename = getNextFileName(albumId, filename);
        saveFile(file, albumId, filename);

        Photo photo = new Photo();
        photo.setOriginalUrl("/photos/original/" + albumId + "/" + filename);
        photo.setThumbUrl("/photos/thumb/" + albumId + "/" + filename);
        photo.setFileName(filename);
        photo.setFileSize(fileSize);
        photo.setAlbum(album);
        Photo savedPhoto = photoRepository.save(photo);

        return PhotoMapper.convertToDto(savedPhoto);
    }

    private String getNextFileName(Long albumId, String filename) {
        String fileNameWithoutEx = StringUtils.stripFilenameExtension(filename);
        String ext = StringUtils.getFilenameExtension(filename);

        Optional<Photo> photo = photoRepository.findIsSamePhotoInAlbum(filename, albumId);

        int count = 2;
        while (photo.isPresent()) {
            filename = String.format("%s (%d).%s", fileNameWithoutEx, count, ext);
            photo = photoRepository.findIsSamePhotoInAlbum(filename, albumId);
            count++;
        }

        return filename;
    }

    private void saveFile(MultipartFile file, Long albumId, String filename) throws IOException {
        try {
            String filePath = albumId + "/" + filename;
            Files.copy(file.getInputStream(), Paths.get(Constants.ORIGINAL_PATH + "/" + filePath));

            BufferedImage thumbImg = Scalr.resize(ImageIO.read(file.getInputStream()), Constants.THUMB_SIZE, Constants.THUMB_SIZE);
            File thumbFile = new File(Constants.THUMB_PATH + "/" + filePath);
            String ext = StringUtils.getFilenameExtension(filename);
            ImageIO.write(thumbImg, ext, thumbFile);
        } catch (Exception e) {
            throw new RuntimeException("에러! 파일을 저장할 수 없습니다." + e.getMessage());
        }
    }

    public File getImageFile(Long photoId) {
        Photo photo = photoRepository.findById(photoId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("에러! ID가 %d인 사진을 찾을 수 없습니다.", photoId)));

        return new File(Constants.PATH_PREFIX + photo.getOriginalUrl());
    }

    @Transactional
    public void movePhoto(Long fromAlbumId, Long toAlbumId, List<Long> photoIds) throws IOException {
        for (Long photoId : photoIds) {
            Photo photo = photoRepository.findById(photoId)
                    .orElseThrow(() -> new NoSuchElementException(String.format("에러! %d번째 사진이 없습니다.", photoId)));

            InputStream oriInputStream = new FileInputStream(Constants.PATH_PREFIX + photo.getOriginalUrl());
            Files.copy(oriInputStream, Paths.get(Constants.ORIGINAL_PATH + "/" + toAlbumId + "/" + photo.getFileName()));
            Files.delete(Path.of(Constants.PATH_PREFIX + photo.getOriginalUrl()));
            photo.setOriginalUrl("/photos/original/" + toAlbumId + "/" + photo.getFileName());

            FileInputStream thumbInputStream = new FileInputStream(Constants.PATH_PREFIX + photo.getThumbUrl());
            Files.copy(thumbInputStream, Paths.get(Constants.THUMB_PATH + "/" + toAlbumId + "/" + photo.getFileName()));
            Files.delete(Path.of(Constants.PATH_PREFIX + photo.getThumbUrl()));
            photo.setThumbUrl("/photos/thumb/" + toAlbumId + "/" + photo.getFileName());

            photoRepository.updatePhotoByAlbumId(fromAlbumId, toAlbumId, photo.getPhotoId());
        }
    }

    @Transactional
    public void deletePhotos(Long albumId, List<Long> photoIds) throws IOException {
        for (Long photoId : photoIds) {
            Photo photo = photoRepository.findPhoto(albumId, photoId)
                    .orElseThrow(() -> new NoSuchElementException(String.format("에러! %d번째 사진이 없습니다.", photoId)));

            Files.delete(Path.of(Constants.PATH_PREFIX + photo.getOriginalUrl()));
            Files.delete(Path.of(Constants.PATH_PREFIX + photo.getThumbUrl()));

            photoRepository.deleteById(photoId);
        }
    }
}
