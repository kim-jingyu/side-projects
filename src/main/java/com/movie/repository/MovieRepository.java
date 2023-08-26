package com.movie.repository;

import com.movie.domain.Movie;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MovieRepository extends MongoRepository<Movie, String> {
    List<Movie> findByTrashed(boolean trashed, Sort sort);
}
