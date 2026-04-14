package me.scpark.springdeveloper;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.scpark.springdeveloper.dao.Article;
import me.scpark.springdeveloper.dto.AddArticleRequest;
import me.scpark.springdeveloper.repository.BlogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BlogApiControllerTest {
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected BlogRepository blogRepository;

    @BeforeEach
    public void deleteAll() {
        blogRepository.deleteAll();
    }


    @DisplayName("addArticle: creates an article")
    @Test
    void addArticle() throws Exception {
        final String url = "/api/articles";
        final String title = "title";
        final String content = "content";
        final AddArticleRequest article = new AddArticleRequest(title, content);
        final String requestBody = objectMapper.writeValueAsString(article);

        ResultActions result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        result.andExpect(status().isCreated());

        List<Article> articles = blogRepository.findAll();
        assertThat(articles).hasSize(1);
        assertThat(articles.getFirst().getTitle()).isEqualTo(title);
        assertThat(articles.getFirst().getContent()).isEqualTo(content);
    }

    @DisplayName("findAllArticles: returns article list")
    @Test
    void findAllArticles() throws Exception {
        final String url = "/api/articles";
        blogRepository.save(Article.builder().title("title").content("content").build());

        ResultActions resultActions = mockMvc.perform(get(url)
                .accept(MediaType.APPLICATION_JSON_VALUE));

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].content").value("content"))
                .andExpect(jsonPath("$[0].title").value("title"));
    }
    @DisplayName("findArticle: 블로그 글 조회에 성공한다.")
    @Test
    public void findArticle() throws Exception {
        final String url = "/api/articles/{id}";
        final String title = "불로그 제목";
        final String content = "불로그 내용";

        Article savedArticle = blogRepository.save(Article.builder().title(title).content(content).build());

        final ResultActions resultActions = mockMvc.perform(get(url, savedArticle.getId()));

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(title))
                .andExpect(jsonPath("$.content").value(content));









    }
}
