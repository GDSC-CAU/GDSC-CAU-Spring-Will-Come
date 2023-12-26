package cau.gdsc.controller;

import cau.gdsc.config.api.ApiResponse;
import cau.gdsc.dto.auth.AuthReqDto;
import cau.gdsc.dto.auth.AuthResDto;
import cau.gdsc.dto.auth.RegisterReqDto;
import cau.gdsc.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ApiResponse<AuthResDto> register(@RequestBody RegisterReqDto reqDto){
        return ApiResponse.created(authService.register(reqDto));
    }

    @PostMapping("/authenticate")
    public ApiResponse<AuthResDto> login(@RequestBody AuthReqDto reqDto){
        return ApiResponse.success(authService.authenticate(reqDto));
    }
}
