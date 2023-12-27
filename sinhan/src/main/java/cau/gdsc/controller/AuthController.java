package cau.gdsc.controller;

import cau.gdsc.config.api.ApiResponse;
import cau.gdsc.dto.auth.AuthReqDto;
import cau.gdsc.dto.auth.SignUpReqDto;
import cau.gdsc.dto.auth.AuthResDto;
import cau.gdsc.dto.auth.TokenRefreshReqDto;
import cau.gdsc.service.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"인증"}, value = "인증 API")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @ApiOperation(value = "회원가입")
    @PostMapping("/signup")
    public ApiResponse<AuthResDto> signUp(@RequestBody SignUpReqDto reqDto) {
        return ApiResponse.created(authService.signUp(reqDto));
    }

    @ApiOperation(value = "로그인")
    @PostMapping("/signin")
    public ApiResponse<AuthResDto> signIn(@RequestBody AuthReqDto reqDto) {
        return ApiResponse.success(authService.signIn(reqDto));
    }

    @ApiOperation(value = "로그아웃")
    @PostMapping("/logout")
    public ApiResponse<Void> logout(@RequestBody AuthReqDto reqDto) {
        authService.logout(reqDto);
        return ApiResponse.success(null);
    }

    @ApiOperation(value = "액세스 토큰 갱신")
    @PostMapping("/refresh")
    public ApiResponse<AuthResDto> refresh(@RequestBody TokenRefreshReqDto reqDto) {
        return ApiResponse.success(authService.refreshToken(reqDto));
    }
}
