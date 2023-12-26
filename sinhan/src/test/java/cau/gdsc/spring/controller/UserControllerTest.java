package cau.gdsc.spring.controller;

import cau.gdsc.config.api.ResponseCode;
import cau.gdsc.controller.UserController;
import cau.gdsc.domain.User;
import cau.gdsc.dto.user.UserResDto;
import cau.gdsc.dto.user.UserUpdateReqDto;
import cau.gdsc.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// MVC 관련 컴포넌트(컨트롤러, 필터, 컨버터, ControllerAdvice 등)만 스캔 후 테스트
@WebMvcTest({UserController.class})
@AutoConfigureDataJpa // EnableAuditing 사용시 이 어노테이션을 추가해야함
public class UserControllerTest {
    @Autowired
    MockMvc mockMvc; // 가짜 요청 주체

    @Autowired
    ObjectMapper objectMapper; // 객체 -> JSON

    @MockBean // 가짜 객체를 Bean으로 등록
    UserService userService;

    private User user1;
    private User user2;

    @BeforeEach
    void setup() {
        user1 = User.builder().name("NEW USER").height(160).weight(60).gender(0).age(30).build();
        user2 = User.builder().name("SECONDARY").height(185).weight(75).gender(1).age(28).build();

        ReflectionTestUtils.setField(user1, "id", 1L);
        ReflectionTestUtils.setField(user2, "id", 2L);
    }

    @Test
    @DisplayName(value = "사용자 전체 조회")
    void listAllUsers() throws Exception {
        List<UserResDto> users = Stream.of(user1, user2)
                .map(UserResDto::of)
                .collect(Collectors.toList());
        when(userService.getUsers()).thenReturn(users);

        mockMvc.perform(get("/user"))
                .andExpect(jsonPath("$.header.code", is(200)))
                .andExpect(jsonPath("$.size()", is(users.size()))); // Response body 확인
    }

    @Test
    @DisplayName(value = "사용자 조회")
    void getUser() throws Exception {
        final Long WRONG_ID = 100L;
        when(userService.getUserById(user1.getId())).thenReturn(UserResDto.of(user1));
        when(userService.getUserById(WRONG_ID)).thenThrow(new EntityNotFoundException("No User"));

        mockMvc.perform(get("/user/{id}", user1.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.header.code", is(ResponseCode.OK.getStatusCode())))
                .andExpect(jsonPath("$.data.name", is(user1.getName())));

        // 예외 처리 테스트
        mockMvc.perform(get("/user/{id}", WRONG_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.header.code", is(404)))
                .andExpect(result ->
                        Assertions.assertTrue(result.getResolvedException() instanceof EntityNotFoundException));
    }

//    @Test
//    @DisplayName(value = "사용자 등록")
//    void registerUser() throws Exception {
//        UserAddReqDto reqDto = UserAddReqDto.builder()
//                .name(user1.getName())
//                .weight(user1.getWeight())
//                .height(user1.getHeight())
//                .gender(user1.getGender())
//                .age(user1.getAge())
//                .build();
//        when(userService.registerUser(any(UserAddReqDto.class))).thenReturn(UserResDto.of(user1));
//
//        mockMvc.perform(post("/user/")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(reqDto)))
//                .andExpect(jsonPath("$.header.code", is(ResponseCode.CREATED.getStatusCode())))
//                .andExpect(jsonPath("$.data.name", is(user1.getName())));
//
//        verify(userService).registerUser(any(UserAddReqDto.class));
//    }

    // 컨트롤러 응답이 정확한지에 대한 단위 테스트이므로 서비스 레이어의 테스트는 생략
    @Test
    @DisplayName(value = "사용자 정보 수정")
    void updateUserInfo() throws Exception {
        UserUpdateReqDto reqDto = UserUpdateReqDto.builder()
                .userId(user1.getId())
                .height(user1.getHeight() + 5)
                .weight(user1.getWeight() + 5)
                .build();
        when(userService.updateUser(any(UserUpdateReqDto.class))).thenReturn(UserResDto.of(user1)); //eq
        mockMvc.perform(put("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reqDto)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName(value = "사용자 삭제")
    void deleteUser() throws Exception {
        final Long NON_EXISTED_ID = 100L;
        // 반환값이 없는 로직에 대한 stub 정의
        doNothing().when(userService).deleteUser(user1.getId());
        doThrow(new EntityNotFoundException("No User")).when(userService).deleteUser(NON_EXISTED_ID);

        mockMvc.perform(delete("/user/{id}", user1.getId()))
                .andExpect(jsonPath("$.header.code", is(ResponseCode.NO_CONTENT.getStatusCode())));

        mockMvc.perform(delete("/user/{id}", NON_EXISTED_ID))
                .andExpect(jsonPath("$.header.code", is(ResponseCode.NOT_FOUND.getStatusCode())));

        verify(userService, times(2)).deleteUser(anyLong());
    }
}
