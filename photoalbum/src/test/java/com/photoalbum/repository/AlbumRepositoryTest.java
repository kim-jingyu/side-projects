package com.photoalbum.repository;

import com.photoalbum.domain.Album;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class AlbumRepositoryTest {
    @Autowired
    AlbumRepository albumRepository;

    @Test
    void 앨범정렬기능() throws InterruptedException {
        Album album1 = new Album();
        Album album2 = new Album();
        album1.setAlbumName("aaaa");
        album2.setAlbumName("aaab");

        album1.setCreatedAt(LocalDateTime.now());
        albumRepository.save(album1);
        TimeUnit.SECONDS.sleep(1);
        album2.setCreatedAt(LocalDateTime.now());
        albumRepository.save(album2);

//         최신순 정렬. 두번째로 생성한 앨범이 먼저 나와야한다.
        List<Album> sortedByDate = albumRepository.findByAlbumNameContainingOrderByCreatedAtDesc("aaa");
        assertThat(sortedByDate.get(0).getAlbumName()).isEqualTo("aaab");
        assertThat(sortedByDate.get(1).getAlbumName()).isEqualTo("aaaa");
        assertThat(sortedByDate.size()).isEqualTo(2);

        // 앨범명 정렬. 앨범a, 앨범b 순서로 나와야한다.
        List<Album> sortedByName = albumRepository.findByAlbumNameContainingOrderByAlbumNameAsc("aaa");
        assertThat(sortedByName.get(0).getAlbumName()).isEqualTo("aaaa");
        assertThat(sortedByName.get(1).getAlbumName()).isEqualTo("aaab");
        assertThat(sortedByName.size()).isEqualTo(2);
    }
}