package cau.gdsc.service;

import cau.gdsc.domain.User;
import cau.gdsc.dto.auth.AuthReqDto;
import cau.gdsc.dto.auth.AuthResDto;
import cau.gdsc.dto.auth.RegisterReqDto;
import cau.gdsc.repository.UserRepository;
import cau.gdsc.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {
    // AuthConfig 참조
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    // UserController에서 회원 등록을 관리했지만
    // 인증 시스템이 생기면서 이를 분리하게 되었다. 이에 따른 DTO도 개편
    public AuthResDto register(RegisterReqDto reqDto) {
        if (userRepository.existsByEmail(reqDto.getEmail())) throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        User newUser = userRepository.save(reqDto.toEntity(passwordEncoder));

        // 등록 완료 후 토큰 반환
        String token = jwtUtils.generateToken((UserDetails) newUser);
        return AuthResDto.of(token);
    }

    public AuthResDto authenticate(AuthReqDto reqDto) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                reqDto.getEmail(),
                reqDto.getPassword()
        )); // auth manager가 인증 및 예외 처리를 모두 담당
        User user = userRepository
                .findByEmail(reqDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이메일입니다."));

        String token = jwtUtils.generateToken((UserDetails) user);
        return AuthResDto.of(token);
    }
}
