package cau.gdsc.dto;

import cau.gdsc.domain.Article;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access=AccessLevel.PRIVATE)
public class ArticleReqDto {
    private Long id;
    private String title;
    private String content;

    public static ArticleReqDto of(Article article){
        return new ArticleReqDto(article.getId(), article.getTitle(), article.getContent());
    }
}
