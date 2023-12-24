package cau.gdsc.controller;

import cau.gdsc.config.api.ApiResponse;
import cau.gdsc.domain.User;
import cau.gdsc.dto.user.UserAddReqDto;
import cau.gdsc.dto.user.UserResDto;
import cau.gdsc.dto.user.UserUpdateReqDto;
import cau.gdsc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public ApiResponse<List<UserResDto>> getUsers() {
        return ApiResponse.success(userService.getUsers());
    }

    @GetMapping("/{id}")
    public ApiResponse<UserResDto> getUserById(@PathVariable Long id) {
        return ApiResponse.success(userService.getUserById(id));
    }

    @PostMapping("")
    public ApiResponse<UserResDto> registerUser(@RequestBody UserAddReqDto reqDto) {
        return ApiResponse.created(userService.registerUser(reqDto));
    }

    @PutMapping("/{id}")
    public ApiResponse<UserResDto> updateUser(@PathVariable Long id, @RequestBody UserUpdateReqDto reqDto) {
        return ApiResponse.success(userService.updateUser(id, reqDto));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ApiResponse.noContent();
    }
}
