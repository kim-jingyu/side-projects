package com.movie.controller;

import com.movie.crawler.Crawler;
import com.movie.crawler.MovieService;
import com.movie.domain.Movie;
import com.movie.dto.MovieResponse;
import com.movie.dto.MovieListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
public class MovieController {
    private final Crawler crawler;
    private final MovieService movieService;

    @GetMapping("/movie/crawl")
    public ResponseEntity initDb() throws IOException {
        List<Movie> movies = crawler.getMovies();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(movies);
    }

    @GetMapping("/movie/list")
    public ResponseEntity getMovieList(@RequestParam(defaultValue = "likes") String sortMode, @RequestParam(defaultValue = "false") boolean trashMode) {
        List<Movie> movies = movieService.getMovieList(trashMode, sortMode);

        if (movies == null) {
            return ResponseEntity.badRequest()
                    .body(new MovieListResponse("failure", sortMode + " 순으로 영화 목록 받아오기 실패!", null));
        }

        return ResponseEntity.ok()
                .body(new MovieListResponse("success", "영화 리스트 GET 요청 완료!", movies));
    }

    // 좋아요
    @PostMapping("/movie/like")
    public ResponseEntity likeMovie(@RequestParam String id) {
        try {
            movieService.likeMovie(id);
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest()
                    .body(new MovieResponse("failure", "좋아요 실패!"));
        }
        return ResponseEntity.ok()
                .body(new MovieResponse("success", "좋아요 성공!"));
    }

    // 휴지통으로
    @PostMapping("/movie/trash")
    public ResponseEntity toTrash(@RequestParam String id) {
        try {
            movieService.toTrash(id);
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest()
                    .body(new MovieResponse("failure", "휴지통으로 보내기 실패!"));
        }

        return ResponseEntity.ok()
                .body(new MovieResponse("success", "휴지통으로 보내기 성공!"));
    }

    // 휴지통에서 복구
    @PostMapping("/movie/trash/restore")
    public ResponseEntity restoreFromTrash(@RequestParam String id) {
        try {
            movieService.restoreFromTrash(id);
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest()
                    .body(new MovieResponse("failure", "휴지통에서 복구하기 실패!"));
        }

        return ResponseEntity.ok()
                .body(new MovieResponse("success", "휴지통으로 복구하기 성공!"));
    }

    // 휴지통에서 삭제
    @DeleteMapping("/movie/trash")
    public ResponseEntity deleteMovie(@RequestParam String id) {
        try {
            movieService.deleteMovie(id);
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest()
                    .body(new MovieResponse("failure", "휴지통에서 영구 삭제 실패!"));
        }

        return ResponseEntity.ok()
                .body(new MovieResponse("success", "휴지통에서 영구 삭제 성공!"));
    }
}
