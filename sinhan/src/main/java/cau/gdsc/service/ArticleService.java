package cau.gdsc.service;

import cau.gdsc.domain.Article;
import cau.gdsc.dto.ArticleReqDto;
import cau.gdsc.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public List<Article> getArticles() {
        return articleRepository.findAll();
    }

    public Article getArticleById(Long id){
        return articleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid article Id:" + id));
    }

    public Article createArticle(ArticleReqDto articleReqDto) {
        return articleRepository.save(Article.createArticle(articleReqDto));
    }

    public Article updateArticle(Long id, ArticleReqDto articleReqDto){
        Article article = getArticleById(id); // 존재 확인
        Article updatedArticle = Article.builder().id(id).title(articleReqDto.getTitle()).content(articleReqDto.getContent()).build();
        return articleRepository.save(article);
    }

    public void deleteArticleById(Long id){
        if(articleRepository.existsById(id)) articleRepository.deleteById(id);
        else throw new IllegalArgumentException("Invalid article Id:" + id);
    }
}
