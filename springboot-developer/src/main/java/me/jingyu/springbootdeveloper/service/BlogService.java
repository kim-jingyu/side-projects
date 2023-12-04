package me.jingyu.springbootdeveloper.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.jingyu.springbootdeveloper.domain.Article;
import me.jingyu.springbootdeveloper.dto.AddArticleRequest;
import me.jingyu.springbootdeveloper.dto.UpdateArticleRequest;
import me.jingyu.springbootdeveloper.repository.BlogRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
@Slf4j
public class BlogService {
    private final BlogRepository blogRepository;

    @Transactional
    public Article save(AddArticleRequest request, String username) {
        log.info("블로그 글 저장");
        return blogRepository.save(request.toEntity(username));
    }

    public List<Article> findAll() {
        return blogRepository.findAll();
    }

    public Article findById(Long id) {
        return blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));
    }

    @Transactional
    public void delete(Long id) {
        log.info("블로그 글 삭제");
        Article article = blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found" + id));

        authorizeArticleAuthor(article);
        blogRepository.delete(article);
    }

    @Transactional
    public Article update(Long id, UpdateArticleRequest request) {
        log.info("블로그 글 수정");
        Article article = blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));

        authorizeArticleAuthor(article);
        article.update(request.getTitle(), request.getContent());

        return article;
    }

    // 요청 헤더에 전달된 토큰을 이용하여 게시글을 작성한 유저인지 검증
    private void authorizeArticleAuthor(Article article) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!article.getAuthor().equals(username)) {
            throw new IllegalArgumentException("not authorized");
        }
    }
}
