package cau.gdsc.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity // Spring Security 설정을 활성화
public class SecurityConfig {
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests() // 요청에 대한 인증 설정
                .anyRequest() // 어떤 요청에 대해서도
                .authenticated() // 인증을 요구
                .and()
                .httpBasic(); // HTTP Basic 인증 사용. 즉, 요청 헤더에 username과 password를 실어 보내야 함
        return http.build();
    }
}
