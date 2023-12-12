package cau.gdsc.controller;

import cau.gdsc.config.api.ApiResponse;
import cau.gdsc.domain.Article;
import cau.gdsc.dto.ArticleAddReqDto;
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
    public ArticleController(ArticleService articleService){
        this.articleService = articleService;
    }

    @GetMapping("/")
    public ApiResponse<List<Article>> getArticles() {
        return ApiResponse.success(articleService.getArticles());
    }

    @GetMapping("/{id}")
    public ApiResponse<Article> getArticleById(@PathVariable Long id){
        return ApiResponse.success(articleService.getArticleById(id));
    }

    @PostMapping("/")
    public ResponseEntity<Article> createArticle(@RequestBody ArticleAddReqDto articleAddReqDto){
        return new ResponseEntity<>(articleService.createArticle(articleAddReqDto), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Article> updateArticle(@PathVariable Long id, @RequestBody ArticleAddReqDto articleAddReqDto){
        return new ResponseEntity<>(articleService.updateArticle(id, articleAddReqDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticleById(@PathVariable Long id){
        try{
            articleService.deleteArticleById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
