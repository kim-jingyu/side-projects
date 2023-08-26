package com.movie.crawler;

import com.movie.domain.Movie;
import com.movie.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;

    public List<Movie> getMovieList(boolean trashMode, String sortMode) {
        return movieRepository.findByTrashed(trashMode, Sort.by(sortMode).descending());
    }
}
