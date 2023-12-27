package cau.gdsc.controller;

import cau.gdsc.config.api.ApiResponse;
import cau.gdsc.dto.article.ArticleAddReqDto;
import cau.gdsc.dto.article.ArticleResDto;
import cau.gdsc.dto.article.ArticleUpdateReqDto;
import cau.gdsc.service.ArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"게시글"}, value = "게시글 API")
@RestController
@RequestMapping("/api/v1/article")
public class ArticleController {
    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @ApiOperation(value = "게시글 목록 조회")
    @GetMapping("")
    public ApiResponse<List<ArticleResDto>> getArticles() {
        return ApiResponse.success(articleService.getArticles());
    }

    @ApiOperation(value = "특정 게시글 조회")
    @GetMapping("/{id}")
    public ApiResponse<ArticleResDto> getArticleById(@PathVariable Long id) {
        return ApiResponse.success(articleService.getArticleById(id));
    }

    // TODO: ID를 세션 또는 토큰으로 대체
//    @ApiOperation(value = "특정 사용자 게시글 목록 조회")
//    @GetMapping("/user/{id}")
//    public ApiResponse<List<ArticleResDto>> getArticlesByUserId(@PathVariable Long id) {
//        return ApiResponse.success(articleService.getArticlesByUserId(id));
//    }

    @ApiOperation(value = "게시글 생성")
    @PostMapping("")
    public ApiResponse<ArticleResDto> createArticle(@RequestBody ArticleAddReqDto articleAddReqDto) {
        return ApiResponse.created(articleService.createArticle(articleAddReqDto));
    }

    @ApiOperation(value = "게시글 수정")
    @PutMapping("")
    public ApiResponse<ArticleResDto> updateArticle(@RequestBody ArticleUpdateReqDto reqDto) {
        return ApiResponse.success(articleService.updateArticle(reqDto));
    }

    @ApiOperation(value = "게시글 삭제")
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteArticleById(@PathVariable Long id) {
        articleService.deleteArticleById(id);
        return ApiResponse.noContent();
    }
}
