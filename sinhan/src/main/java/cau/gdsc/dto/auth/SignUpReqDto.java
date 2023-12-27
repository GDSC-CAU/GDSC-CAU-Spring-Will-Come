package cau.gdsc.dto.auth;

import cau.gdsc.domain.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

// 회원 가입 요청 DTO

@ApiModel(description = "회원 가입 요청 데이터")
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SignUpReqDto {
    @ApiModelProperty(value = "회원 이름", example = "홍길동", required = true)
    private String name;
    @ApiModelProperty(value = "키", example = "175", required = true)
    private int height;
    @ApiModelProperty(value = "몸무게", example = "70", required = true)
    private int weight;
    @ApiModelProperty(value = "성별(0: 남성, 1: 여성)", example = "0", required = true)
    private int gender;
    @ApiModelProperty(value = "나이", example = "30", required = true)
    private int age;
    @ApiModelProperty(value = "이메일", example = "abc@gmail.com", required = true)
    private String email;
    @ApiModelProperty(value = "비밀번호", example = "1q2w3e4r", required = true)
    private String password;

    public User toEntity(PasswordEncoder passwordEncoder){
        return User
                .builder()
                .name(this.name)
                .height(this.height)
                .gender(this.gender)
                .age(this.age)
                .email(this.email)
                .password(passwordEncoder.encode(this.password)) // 해쉬 암호화. AuthConfig 참조
                .build();
    }
}
