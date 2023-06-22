package me.jingyu.springbootdeveloper.service;

import lombok.RequiredArgsConstructor;
import me.jingyu.springbootdeveloper.domain.Article;
import me.jingyu.springbootdeveloper.dto.AddArticleRequest;
import me.jingyu.springbootdeveloper.repository.BlogRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BlogService {
    private final BlogRepository blogRepository;

    public Article save(AddArticleRequest request) {
        return blogRepository.save(request.toEntity());
    }
}
