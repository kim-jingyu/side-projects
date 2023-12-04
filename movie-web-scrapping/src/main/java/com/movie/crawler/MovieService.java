package com.movie.crawler;

import com.movie.domain.Movie;
import com.movie.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;
    private final MongoTemplate mongoTemplate;

    public List<Movie> getMovieList(boolean trashMode, String sortMode) {
        if (sortMode.equals("likes")){
            return movieRepository.findByTrashed(trashMode, Sort.by("likes").descending());
        } else if (sortMode.equals("viewers")) {
            return movieRepository.findByTrashed(trashMode, Sort.by("audience").descending());
        } else if (sortMode.equals("date")) {
            Sort sort = Sort.by(
                    Sort.Order.desc("openYear"),
                    Sort.Order.desc("openMonth"),
                    Sort.Order.desc("openDay")
            );
            return movieRepository.findByTrashed(trashMode, sort);
        }
        return null;
    }

    @Transactional
    public void likeMovie(String id) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);
        movie.likeMovie();
        movieRepository.save(movie);
    }

    @Transactional
    public void toTrash(String id) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);
        movie.toTrash();
        movieRepository.save(movie);
    }

    @Transactional
    public void restoreFromTrash(String id) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);
        movie.restoreFromTrash();
        movieRepository.save(movie);
    }

    @Transactional
    public void deleteMovie(String id) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);
        movieRepository.delete(movie);
    }
}
