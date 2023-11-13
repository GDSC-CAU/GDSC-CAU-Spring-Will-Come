package cau.gdsc.dto;

import cau.gdsc.domain.Article;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ArticleReqDto {
    private Long userId;
    private String title;
    private String content;

    public static ArticleReqDto from(Article article) {
        return new ArticleReqDto(article.getUser().getId(), article.getTitle(), article.getContent());
    }
}
