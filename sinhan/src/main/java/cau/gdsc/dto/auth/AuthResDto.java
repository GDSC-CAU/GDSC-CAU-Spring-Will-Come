package cau.gdsc.dto.auth;

import cau.gdsc.domain.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthResDto {
    private String token;

    public static AuthResDto of(String token){
        return new AuthResDto(token);
    }
}
