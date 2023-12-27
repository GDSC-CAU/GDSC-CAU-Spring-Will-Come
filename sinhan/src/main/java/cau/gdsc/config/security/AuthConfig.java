package cau.gdsc.config.security;

import cau.gdsc.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

// JWT 인증에 필요한 Bean 관리
@Configuration
@RequiredArgsConstructor
public class AuthConfig {
    private final UserRepository userRepository;

    // 사용자 정보를 DB로부터 가져온다(UserDetailsService).
    // 이 인터페이스는 loadUserByUsername 메서드 단 하나만 정의되어 있다.
    // 따라서, 람다식(함수형 인터페이스)으로 구현하여 사용한다.
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> (UserDetails) userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User email not found: " + username));
    }

    // UserDetailsService와 PasswordEncoder를 사용하여 인증을 처리한다.
    // 여기선 DaoAuthenticationProvider를 사용하는데 DB로부터 사용자 정보를 가져와 DB의 해쉬 비밀번호와 입력받은 비밀번호를 비교(matches 메서드)한다.
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder()); // UserDetails의 비밀번호와 입력받은 비밀번호를 비교
        return provider;
    }

    // AuthenticationManager는 AuthenticationProvider를 통해 인증(authenticate 메서드)을 처리한다. (AuthService 참조)
    // 즉, AuthenticationProvider에게 인증을 위임하고 그 결과를 반환한다.
    // AuthenticationConfiguration은 스프링이 자동으로 생성하는 것으로, 의존성 주입받아 AuthenticationManager를 관리하도록 설정하는 것이다.
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    // 비밀번호 암호화. DB에 비밀번호를 암호화해서 저장할때 사용
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
