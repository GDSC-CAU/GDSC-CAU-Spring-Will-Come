package cau.gdsc.dto.article;

import cau.gdsc.domain.Article;
import cau.gdsc.domain.User;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class ArticleResDto {
    private Long id;
    private String title;
    private String content;
    private User user;

    public static ArticleResDto of(Article article) {
        return ArticleResDto.builder()
                .id(article.getId())
                .title(article.getTitle())
                .content(article.getContent())
                .user(article.getUser())
                .build();
    }
}