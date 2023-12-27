package cau.gdsc.dto.auth;

import cau.gdsc.domain.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@ApiModel(description = "인증 성공시 응답 데이터")
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthResDto {
    @ApiModelProperty(value = "응답 JWT 토큰")
    private String token;

    public static AuthResDto of(String token){
        return new AuthResDto(token);
    }
}
