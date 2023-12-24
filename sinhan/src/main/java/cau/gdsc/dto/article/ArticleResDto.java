package cau.gdsc.dto.article;

import cau.gdsc.domain.Article;
import cau.gdsc.domain.User;
import cau.gdsc.dto.user.UserResDto;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class ArticleResDto {
    private Long id;
    private String title;
    private String content;
    private UserResDto user;

    public static ArticleResDto of(Article article) {
        User user = article.getUser();
        return ArticleResDto.builder()
                .id(article.getId())
                .title(article.getTitle())
                .content(article.getContent())
                .user(UserResDto.of(user))
                .build();
    }
}