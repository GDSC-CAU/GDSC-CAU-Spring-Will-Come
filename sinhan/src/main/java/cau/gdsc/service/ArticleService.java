package cau.gdsc.service;

import cau.gdsc.config.api.ResponseCode;
import cau.gdsc.config.exception.BaseException;
import cau.gdsc.domain.Article;
import cau.gdsc.domain.User;
import cau.gdsc.dto.article.ArticleAddReqDto;
import cau.gdsc.dto.article.ArticleResDto;
import cau.gdsc.dto.article.ArticleUpdateReqDto;
import cau.gdsc.repository.ArticleRepository;
import cau.gdsc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    @Autowired
    public ArticleService(ArticleRepository articleRepository, UserRepository userRepository) {

        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
    }

    public List<ArticleResDto> getArticles() {
        return articleRepository
                .findAll()
                .stream()
                .map(ArticleResDto::of)
                .collect(Collectors.toList());
    }

    public ArticleResDto getArticleById(Long id) {
        return ArticleResDto.of(findArticleById(id));
    }

    public List<ArticleResDto> getArticlesByUserId(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
        return articleRepository
                .findAllByUser(user)
                .stream()
                .map(ArticleResDto::of)
                .collect(Collectors.toList());
    }

    public ArticleResDto createArticle(ArticleAddReqDto articleAddReqDto) {
        User user = userRepository.findById(articleAddReqDto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + articleAddReqDto.getUserId()));

        Article newArticle = Article
                .builder()
                .title(articleAddReqDto.getTitle())
                .content(articleAddReqDto.getContent())
                .user(user)
                .build();
        articleRepository.save(newArticle);
        return ArticleResDto.of(newArticle);
    }

    public ArticleResDto updateArticle(ArticleUpdateReqDto reqDto) {
        Article updatedArticle = findArticleById(reqDto.getArticleId()); // 존재 확인
        updatedArticle.update(reqDto.getTitle(), reqDto.getContent());
        return ArticleResDto.of(updatedArticle);
    }

    public void deleteArticleById(Long id) {
        Article target = findArticleById(id);
        articleRepository.delete(target);
    }

    private Article findArticleById(Long id) {
        return articleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Article not found with id: " + id));
    }
}
