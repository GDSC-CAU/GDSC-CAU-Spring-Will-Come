package cau.gdsc.dto.auth;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@ApiModel(description = "리프레시 토큰 갱신 요청 데이터")
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TokenRefreshReqDto {
    @ApiModelProperty(value = "리프레시 토큰")
    private String refreshToken;
}
