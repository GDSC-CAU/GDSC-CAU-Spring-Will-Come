package cau.gdsc.spring.service;

import cau.gdsc.domain.User;
import cau.gdsc.dto.UserAddReqDto;
import cau.gdsc.repository.UserRepository;
import cau.gdsc.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class) // 스프링 컨테이너 로드없이 테스트. 빈 객체 주입을 별도 진행해야한다.
@ActiveProfiles("test")
public class UserServiceTest {
    @InjectMocks
    private UserService userService; // 모의 객체를 주입받는 객체

    // 모의 DAO
    // 모의 객체는 실제로 데이터를 조작하지 않는다.
    // 대신에 stub(행동)을 선언하여 어떻게 반환할지를 정의한다.
    @Mock
    private UserRepository userRepository;

    private List<User> users;
    private User user1;
    private User user2;

    @BeforeEach
    void setup() {
        user1 = User.of("NEW USER", 160, 60, 0, 30);
        user2 = User.of("SECONDARY", 185, 75, 1, 28);

        // ID에 대한 setter가 없는 엔티티의 필드를 수정하는 방법
        ReflectionTestUtils.setField(user1, "id", 1L);
        ReflectionTestUtils.setField(user2, "id", 2L);
        users = List.of(user1, user2);
    }

    @Test
    @DisplayName(value = "모든 사용자 조회")
    void listAllUsers() {

        // 모의 객체 메서드 반환값 정의. 정의하지 않으면 모의 객체 메서드는 null을 반환한다.
        // 실제로 데이터를 저장, 조회, 삭제하지 않기 때문에 save를 생략한다.
        when(userRepository.findAll()).thenReturn(users);

        List<User> userList = userService.getUsers();

        assertThat(userList.size()).isEqualTo(users.size());
        assertThat(userList).contains(user1, user2);
    }

    @Test
    @DisplayName(value = "특정 사용자 조회")
    void getUser() {
        // 어떤 Long 값을 넣드
        when(userRepository.findById(user1.getId())).thenReturn(Optional.of(user1));

        User retrievedUser = userService.getUserById(user1.getId());

        assertThat(retrievedUser).isNotNull();
        assertThat(retrievedUser.getId()).isEqualTo(user1.getId());

        // 존재하지 않는 사용자 접근 예외 처리 테스트
        assertThatThrownBy(() -> userService.getUserById(10L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("No User");
    }

    @Test
    @DisplayName(value = "사용자 등록")
    void registerUser(){
        UserAddReqDto reqDto = new UserAddReqDto();
        when(userRepository.save(user1)).thenReturn(user1);
        User.builder().
        User newUser = userService.registerUser(user1);

    }
}
