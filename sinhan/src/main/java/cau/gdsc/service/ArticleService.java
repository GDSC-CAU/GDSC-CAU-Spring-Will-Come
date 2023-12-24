package cau.gdsc.service;

import cau.gdsc.config.api.ResponseCode;
import cau.gdsc.config.exception.BaseException;
import cau.gdsc.domain.Article;
import cau.gdsc.dto.article.ArticleAddReqDto;
import cau.gdsc.dto.article.ArticleResDto;
import cau.gdsc.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
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

    public ArticleResDto createArticle(ArticleAddReqDto articleAddReqDto) {
        Article newArticle = articleRepository.save(articleAddReqDto.toEntity());
        return ArticleResDto.of(newArticle);
    }

    public ArticleResDto updateArticle(Long id, ArticleAddReqDto reqDto) {
        Article updatedArticle = findArticleById(id); // 존재 확인
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
