package cau.gdsc.controller;

import cau.gdsc.config.api.ApiResponse;
import cau.gdsc.dto.auth.AuthReqDto;
import cau.gdsc.dto.auth.AuthResDto;
import cau.gdsc.dto.auth.RegisterReqDto;
import cau.gdsc.service.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags={"인증"}, value = "인증 API")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @ApiOperation(value = "회원가입")
    @PostMapping("/register")
    public ApiResponse<AuthResDto> register(@RequestBody RegisterReqDto reqDto){
        return ApiResponse.created(authService.register(reqDto));
    }

    @ApiOperation(value = "로그인")
    @PostMapping("/authenticate")
    public ApiResponse<AuthResDto> login(@RequestBody AuthReqDto reqDto){
        return ApiResponse.success(authService.authenticate(reqDto));
    }
}
