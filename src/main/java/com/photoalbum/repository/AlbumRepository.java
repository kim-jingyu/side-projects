package com.photoalbum.repository;

import com.photoalbum.domain.Album;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AlbumRepository extends JpaRepository<Album, Long> {
    Optional<Album> findByAlbumName(String albumName);

    List<Album> findByAlbumNameContainingOrderByAlbumNameAsc(String keyword);

    List<Album> findByAlbumNameContainingOrderByCreatedAtDesc(String keyword);
}