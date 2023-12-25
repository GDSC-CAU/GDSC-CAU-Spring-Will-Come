package cau.gdsc.dto.article;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@ApiModel(description = "게시글 수정 요청 데이터")
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
@AllArgsConstructor(access = lombok.AccessLevel.PRIVATE)
@Builder
public class ArticleUpdateReqDto {
    @ApiModelProperty(value = "게시글 제목", required = true, example = "제목")
    private String title;
    @ApiModelProperty(value = "게시글 내용", required = true, example = "내용")
    private String content;
    @ApiModelProperty(value = "게시글 ID", required = true, example = "1")
    private Long articleId;
}
