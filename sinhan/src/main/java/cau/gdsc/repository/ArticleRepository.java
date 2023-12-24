package cau.gdsc.repository;

import cau.gdsc.domain.Article;
import cau.gdsc.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    @Query("SELECT a FROM Article a JOIN FETCH a.user") // JOIN FETCH를 통해 N+1 문제 해결
    List<Article> findAllByUser(User user);
}
