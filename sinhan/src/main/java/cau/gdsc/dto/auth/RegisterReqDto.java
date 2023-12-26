package cau.gdsc.dto.auth;

import cau.gdsc.domain.User;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

// 회원 가입 요청 DTO
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RegisterReqDto {

    private String name;
    private int height;
    private int weight;
    private int gender;
    private int age;
    private String email;
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
