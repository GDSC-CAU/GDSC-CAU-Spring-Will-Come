package cau.gdsc.dto.article;

import cau.gdsc.domain.Article;
import cau.gdsc.domain.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(access=AccessLevel.PRIVATE)
@NoArgsConstructor(access=AccessLevel.PRIVATE) // RequestBody로 사용될 경우 ObjectMapper라는 것이 기본 생성자를 통해 DTO를 생성한다.
public class ArticleAddReqDto {
    private String title;
    private String content;
    private Long userId;
}
