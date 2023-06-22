package me.jingyu.springbootdeveloper.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.jingyu.springbootdeveloper.domain.Article;
import me.jingyu.springbootdeveloper.dto.AddArticleRequest;
import me.jingyu.springbootdeveloper.repository.BlogRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BlogApiControllerTest {
    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ObjectMapper objectMapper;    // 직렬화, 역직렬화를 위한 클래스
    @Autowired
    private WebApplicationContext context;
    @Autowired
    BlogRepository blogRepository;

    @BeforeEach
    public void mockMvcSetup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        blogRepository.deleteAll();
    }

    // 블로그 글 생성 api 테스트
    @DisplayName("addArticle: 블로그 글 추가 성공")
    @Test
    void addArticle() throws Exception {
        // given
        // 블로그 글 추가에 필요한 요청 객체 생성
        final String url = "/api/articles";
        final String title = "title";
        final String content = "content";
        final AddArticleRequest userRequest = new AddArticleRequest(title, content);
        // 객체 -> JSON (직렬화)
        String requestBody = objectMapper.writeValueAsString(userRequest);

        // when
        // 블로그 글 추가 API에 요청을 보냄
        ResultActions result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        // then
        // 응답코드가 201 Created인지 확인
        result.andExpect(status().isCreated());

        List<Article> articles = blogRepository.findAll();

        assertThat(articles.size()).isEqualTo(1);
        assertThat(articles.get(0).getTitle()).isEqualTo(title);
        assertThat(articles.get(0).getContent()).isEqualTo(content);
    }

    // 블로그 글 목록 조회 api 테스트
    @DisplayName("findAllArticles: 블로그 글 목록 조회 성공")
    @Test
    void findAllArticles() throws Exception {
        // given
        // 블로그 글 저장
        final String url = "/api/articles";
        final String title = "title";
        final String content = "content";

        blogRepository.save(Article.builder()
                .title(title)
                .content(content)
                .build());

        // when
        // 목록 조회 API를 호출
        ResultActions result = mockMvc.perform(get(url)
                .accept(MediaType.APPLICATION_JSON));

        // then
        // 응답 코드가 0이고, 받환받은 값 중에 0번째 요소의 content와 title이 저장된 값과 같은지 확인
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].content").value(content))
                .andExpect(jsonPath("$[0].title").value(title));
    }

    // 블로그 글 하나 조회 api 테스트
    @DisplayName("findArticle: 블로그 글 하나 조회 성공")
    @Test
    void findArticle() throws Exception {
        // given
        // 블로그 글 저장
        final String url = "/api/articles/{id}";
        final String title = "title";
        final String content = "content";

        Article savedArticle = blogRepository.save(Article.builder()
                .title(title)
                .content(content)
                .build());

        // when
        // 저장한 블로그 글의 id값으로 API 호출
        ResultActions result = mockMvc.perform(get(url, savedArticle.getId()));

        // then
        // 응답 코드가 200 OK이고, 반환받은 content와 title이 저장된 값과 같은지 확인
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value(content))
                .andExpect(jsonPath("$.title").value(title));
    }

    @DisplayName("deleteArticle: 블로그 글 삭제 성공")
    @Test
    void deleteArticle() throws Exception {
        // given
        final String url = "/api/articles/{id}";
        final String title = "title";
        final String content = "content";

        Article savedArticle = blogRepository.save(Article.builder()
                .title(title)
                .content(content)
                .build());

        // when
        ResultActions result = mockMvc.perform(delete(url, savedArticle.getId()));

        // then
        result
                .andExpect(status().isOk());

        List<Article> articles = blogRepository.findAll();

        assertThat(articles).isEmpty();
    }
}