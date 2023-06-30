package me.jingyu.springbootdeveloper.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.jingyu.springbootdeveloper.domain.Article;
import me.jingyu.springbootdeveloper.dto.AddArticleRequest;
import me.jingyu.springbootdeveloper.dto.ArticleResponse;
import me.jingyu.springbootdeveloper.dto.UpdateArticleRequest;
import me.jingyu.springbootdeveloper.service.BlogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@RestController
@Slf4j
public class BlogApiController {
    private final BlogService blogService;

    @PostMapping("/api/articles")
    public ResponseEntity<Article> addArticle(@RequestBody AddArticleRequest request, Principal principal) {    // principal : 현재 인증 정보를 가져옴
        Article savedArticle = blogService.save(request, principal.getName());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedArticle);
    }

    // 글 목록 조회
    @GetMapping("/api/articles")
    public ResponseEntity<List<ArticleResponse>> findAllArticles() {
        List<ArticleResponse> articles = blogService.findAll()
                .stream()
                .map(article -> new ArticleResponse(article))
                .toList();

        return ResponseEntity.ok()
                .body(articles);
    }

    // 글 하나 조회
    @GetMapping("/api/articles/{id}")
    public ResponseEntity<ArticleResponse> findArticle(@PathVariable Long id) {
        Article article = blogService.findById(id);

        return ResponseEntity.ok()
                .body(new ArticleResponse(article));
    }

    // 글 삭제
    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable Long id) {
        blogService.delete(id);

        return ResponseEntity.ok()
                .build();
    }

    // 글 수정
    @PutMapping("/api/articles/{id}")
    public ResponseEntity<Article> updateArticle(@PathVariable Long id, @RequestBody UpdateArticleRequest request) {
        Article updatedArticle = blogService.update(id, request);

        log.info("글 수정 완료");

        return ResponseEntity.ok()
                .body(updatedArticle);
    }
}
