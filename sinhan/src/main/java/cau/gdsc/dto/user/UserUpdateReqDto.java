package cau.gdsc.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@ApiModel(description = "회원 정보 수정 요청 데이터")
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class UserUpdateReqDto {
    @ApiModelProperty(value = "키", required = true)
    private int height;
    @ApiModelProperty(value = "몸무게", required = true)
    private int weight;
    @ApiModelProperty(value = "사용자 ID", required = true)
    private Long userId;
}
