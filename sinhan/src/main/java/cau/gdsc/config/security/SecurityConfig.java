package cau.gdsc.config.security;

import cau.gdsc.security.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity // Spring Security 설정을 활성화
@RequiredArgsConstructor
public class SecurityConfig {
    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthFilter jwtFilter;

    // SecurityFilterChain은 시큐리티 5.0 이후부터 추가된 인터페이스로, 여러 개의 필터를 체인 형태로 관리한다.
    // 원래 인증 설정은 HttpSecurity를 정의하고 WebSecurityConfigurerAdapter를 상속받아서 구현해야 한다.
    // 무상태성 특징을 가진 REST API를 사용하는 경우, 세션(Non-stateless)을 사용하지 않도록 설정해야 한다.
    // 이제부턴 이 Bean이 등록되면서 설정된 모든 엔드포인트에 인증이 요구된다.
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf() // CSRF 공격 방지 기능 비활성화. CSRF는 Cross-Site Request Forgery의 약자로, 웹 사이트의 취약점을 이용하여 사용자가 의도하지 않은 요청을 통해 공격하는 방식
                .disable() // CSRF 공격은 세션 기반의 인증에서 주로 발생하므로, REST API에서는 비활성화해도 좋다.
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(new AntPathRequestMatcher("/api/v1/auth/**")).permitAll() // 인증 관련
                        .requestMatchers(new AntPathRequestMatcher("/swagger-ui/**")).permitAll() // Swagger 관련
                        .requestMatchers(new AntPathRequestMatcher("/swagger-resources/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/v2/api-docs")).permitAll()
                        .anyRequest().authenticated()) // 나머지는 인증 필요
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 서버가 상태를 관리하는 방식인 세션을 사용하지 않음, RESTful 원칙 준수
                .and()
                .authenticationProvider(authenticationProvider) // 실제 인증을 수행하는 Provider를 등록(UsernamePasswordAuthenticationToken에서 사용)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class); // UsernamePasswordAuthenticationFilter 앞에 JwtFilter를 추가
        return http.build();
    }
}
