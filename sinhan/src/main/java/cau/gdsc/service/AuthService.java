package cau.gdsc.service;

import cau.gdsc.config.api.ResponseCode;
import cau.gdsc.config.exception.AuthException;
import cau.gdsc.domain.RefreshToken;
import cau.gdsc.domain.User;
import cau.gdsc.dto.auth.AuthReqDto;
import cau.gdsc.dto.auth.SignUpReqDto;
import cau.gdsc.dto.auth.AuthResDto;
import cau.gdsc.dto.auth.TokenRefreshReqDto;
import cau.gdsc.repository.RefreshTokenRepository;
import cau.gdsc.repository.UserRepository;
import cau.gdsc.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {
    // AuthConfig 참조
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    // UserController에서 회원 등록을 관리했지만
    // 인증 시스템이 생기면서 이를 분리하게 되었다. 이에 따른 DTO도 개편
    public AuthResDto signUp(SignUpReqDto reqDto) {
        if (userRepository.existsByEmail(reqDto.getEmail()))
            throw new AuthException(ResponseCode.UNAUTHORIZED, "이미 존재하는 이메일입니다.");
        User newUser = userRepository.save(reqDto.toEntity(passwordEncoder));

        // 등록 완료 후 토큰 반환
        String accessToken = jwtUtils.generateAccessToken((UserDetails) newUser);
        String refreshToken = jwtUtils.generateRefreshToken((UserDetails) newUser);

        // 리프레시 토큰 저장
        refreshTokenRepository.save(RefreshToken
                .builder()
                .token(refreshToken)
                .user(newUser)
                .build());
        return AuthResDto.of(accessToken, refreshToken);
    }

    public AuthResDto signIn(AuthReqDto reqDto) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                reqDto.getEmail(),
                reqDto.getPassword()
        )); // auth manager가 인증 및 예외 처리를 모두 담당

        User user = userRepository
                .findByEmail(reqDto.getEmail())
                .orElseThrow(() -> new AuthException(ResponseCode.UNAUTHORIZED, "존재하지 않는 이메일입니다."));

        String accessToken = jwtUtils.generateAccessToken((UserDetails) user);
        String refreshToken = jwtUtils.generateRefreshToken((UserDetails) user);

        // 리프레시 토큰 저장
        refreshTokenRepository.save(RefreshToken
                .builder()
                .token(refreshToken)
                .user(user)
                .build());

        return AuthResDto.of(accessToken, refreshToken);
    }

    public void logout(AuthReqDto reqDto) {

    }

    public AuthResDto refreshToken(TokenRefreshReqDto reqDto){
        RefreshToken refreshToken = refreshTokenRepository
                .findByToken(reqDto.getRefreshToken())
                .orElseThrow(() -> new AuthException(ResponseCode.UNAUTHORIZED, "존재하지 않는 리프레시 토큰입니다."));
        if(jwtUtils.isTokenExpired(refreshToken.getToken())) {
            throw new AuthException(ResponseCode.UNAUTHORIZED, "만료된 리프레시 토큰입니다.");
        }
        String accessToken = jwtUtils.generateAccessToken((UserDetails) refreshToken.getUser());
        String newRefreshToken = jwtUtils.generateRefreshToken((UserDetails) refreshToken.getUser());

        refreshToken.updateToken(newRefreshToken);
        return AuthResDto.of(accessToken, newRefreshToken);
    }
}
