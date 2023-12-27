package cau.gdsc.dto.auth;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@ApiModel(description = "회원가입 성공시 응답 데이터")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthResDto {
    @ApiModelProperty(value = "액세스 토큰")
    private String accessToken;
    @ApiModelProperty(value = "리프레시 토큰")
    private String refreshToken;

    public static AuthResDto of(String accessToken, String refreshToken) {
        return new AuthResDto(accessToken, refreshToken);
    }
}
