package com.movie.controller;

import com.movie.crawler.Crawler;
import com.movie.domain.Movie;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MovieController {
    private final Crawler crawler;

    @GetMapping("/movie/crawl")
    public ResponseEntity initDb() throws IOException {
        List<Movie> movies = crawler.getMovies();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(movies);
    }
}
