package cau.gdsc.controller;

import cau.gdsc.config.api.ApiResponse;
import cau.gdsc.domain.Article;
import cau.gdsc.dto.article.ArticleAddReqDto;
import cau.gdsc.dto.article.ArticleResDto;
import cau.gdsc.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController {
    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/")
    public ApiResponse<List<ArticleResDto>> getArticles() {
        return ApiResponse.success(articleService.getArticles());
    }

    @GetMapping("/{id}")
    public ApiResponse<ArticleResDto> getArticleById(@PathVariable Long id) {
        return ApiResponse.success(articleService.getArticleById(id));
    }

    @PostMapping("/")
    public ApiResponse<ArticleResDto> createArticle(@RequestBody ArticleAddReqDto articleAddReqDto) {
        return ApiResponse.created(articleService.createArticle(articleAddReqDto));
    }

    @PatchMapping("/{id}")
    public ApiResponse<ArticleResDto> updateArticle(@PathVariable Long id, @RequestBody ArticleAddReqDto articleAddReqDto) {
        return ApiResponse.success(articleService.updateArticle(id, articleAddReqDto));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteArticleById(@PathVariable Long id) {
        articleService.deleteArticleById(id);
        return ApiResponse.noContent();
    }
}
