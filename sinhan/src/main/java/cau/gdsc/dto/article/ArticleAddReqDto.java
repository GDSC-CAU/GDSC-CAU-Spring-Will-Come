package cau.gdsc.dto.article;

import cau.gdsc.domain.Article;
import cau.gdsc.domain.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@ApiModel(description = "게시글 추가 요청 데이터")
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE) // RequestBody로 사용될 경우 ObjectMapper라는 것이 기본 생성자를 통해 DTO를 생성한다.
public class ArticleAddReqDto {
    @ApiModelProperty(value = "제목", example = "안녕하세요", required = true)
    private String title;
    @ApiModelProperty(value = "내용", example = "반갑습니다", required = true)
    private String content;
    @ApiModelProperty(value = "작성자 ID", example = "1", required = true)
    private Long userId;
}
