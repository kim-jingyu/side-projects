package com.photoalbum.repository;

import com.photoalbum.domain.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PhotoRepository extends JpaRepository<Photo,Long> {
    int countByAlbum_AlbumId(Long albumId);

    // 앨범 아이디별로 최신 4장의 이미지를 가져오는 메서드
    List<Photo> findTop4ByAlbum_AlbumIdOrderByUploadedAtDesc(Long albumId);

    @Query("select p from Photo p join p.album a where a.albumId = :albumId and p.photoId = :photoId")
    Optional<Photo> findPhoto(@Param("albumId") Long albumId, @Param("photoId") Long photoId);
}
