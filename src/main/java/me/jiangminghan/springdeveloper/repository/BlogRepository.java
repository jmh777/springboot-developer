package me.jiangminghan.springdeveloper.repository;

import me.jiangminghan.springdeveloper.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<Article, Long> {
}
