package cau.gdsc.controller;

import cau.gdsc.config.api.ApiResponse;
import cau.gdsc.domain.User;
import cau.gdsc.dto.article.ArticleResDto;
import cau.gdsc.dto.user.UserAddReqDto;
import cau.gdsc.dto.user.UserResDto;
import cau.gdsc.dto.user.UserUpdateReqDto;
import cau.gdsc.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Api(tags = {"User"}, value = "회원 API")
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation(value = "회원 목록 조회")
    @GetMapping("")
    public ApiResponse<List<UserResDto>> getUsers() {
        return ApiResponse.success(userService.getUsers());
    }

    @ApiOperation(value = "특정 회원 조회")
    @GetMapping("/{id}")
    public ApiResponse<UserResDto> getUserById(@PathVariable Long id) {
        return ApiResponse.success(userService.getUserById(id));
    }

    @ApiOperation(value = "회원 등록")
    @PostMapping("")
    public ApiResponse<UserResDto> registerUser(@RequestBody UserAddReqDto reqDto) {
        return ApiResponse.created(userService.registerUser(reqDto));
    }

    @ApiOperation(value = "회원 수정")
    @PutMapping("")
    public ApiResponse<UserResDto> updateUser(@RequestBody UserUpdateReqDto reqDto) {
        return ApiResponse.success(userService.updateUser(reqDto));
    }

    @ApiOperation(value = "회원 삭제")
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ApiResponse.noContent();
    }
}
