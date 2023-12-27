package cau.gdsc.dto.auth;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

// 회원 인증 요청 DTO

@ApiModel(description = "로그인 요청 데이터")
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthReqDto {
    @ApiModelProperty(value = "사용자가 입력한 이메일", example = "abc@gmail.com",required = true)
    private String email;
    @ApiModelProperty(value = "사용자가 입력한 비밀번호", example = "1q2w3e4r",required = true)
    private String password;
}
