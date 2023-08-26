package com.movie.controller;

import com.movie.crawler.Crawler;
import com.movie.crawler.MovieService;
import com.movie.domain.Movie;
import com.movie.dto.MovieResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

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

        MovieResponse movieResponse = new MovieResponse("success", "영화 리스트 GET 요청 완료!", movies);

        return ResponseEntity.ok()
                .body(movieResponse);
    }
}
