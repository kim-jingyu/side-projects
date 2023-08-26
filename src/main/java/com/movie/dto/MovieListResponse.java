package com.movie.dto;

import com.movie.domain.Movie;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter @Setter
public class MovieListResponse {
    private String result;
    private String msg;
    private List<Movie> movieList;
}
