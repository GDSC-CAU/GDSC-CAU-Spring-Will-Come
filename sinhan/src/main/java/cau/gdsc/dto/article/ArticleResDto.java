package cau.gdsc.dto.article;

import cau.gdsc.domain.Article;
import cau.gdsc.domain.User;
import cau.gdsc.dto.user.UserResDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@ApiModel(description = "게시글 응답 데이터") // ApiModel은 Swagger에서 사용될 모델을 설명하는 어노테이션이다.
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class ArticleResDto {
    @ApiModelProperty(value = "게시글 ID", example = "1")
    private Long id;
    @ApiModelProperty(value = "게시글 제목", example = "제목")
    private String title;
    @ApiModelProperty(value = "게시글 내용", example = "내용")
    private String content;
    @ApiModelProperty(value = "게시글 작성자 정보", notes = "UserResDto 참고")
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