package cau.gdsc.spring.service;

import cau.gdsc.domain.User;
import cau.gdsc.dto.user.UserAddReqDto;
import cau.gdsc.dto.user.UserResDto;
import cau.gdsc.dto.user.UserUpdateReqDto;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

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
        user1 = User.builder().name("NEW USER").height(160).weight(60).gender(0).age(30).build();
        user2 = User.builder().name("SECONDARY").height(185).weight(75).gender(1).age(28).build();

        // ID에 대한 setter가 없는 엔티티의 필드를 수정하는 방법
        ReflectionTestUtils.setField(user1, "id", 1L);
        ReflectionTestUtils.setField(user2, "id", 2L);
        users = List.of(user1, user2); // list all test
    }

    @Test
    @DisplayName(value = "모든 사용자 조회")
    void listAllUsers() {

        // 모의 객체 메서드 반환값 정의. 정의하지 않으면 모의 객체 메서드는 null을 반환한다.
        // 실제로 데이터를 저장, 조회, 삭제하지 않기 때문에 save를 생략한다.
        when(userRepository.findAll()).thenReturn(users);

        List<UserResDto> userList = userService.getUsers();

        assertThat(userList.size()).isEqualTo(users.size());
    }

    @Test
    @DisplayName(value = "특정 사용자 조회")
    void getUser() {
        // 어떤 Long 값을 넣드
        when(userRepository.findById(user1.getId())).thenReturn(Optional.of(user1));

        UserResDto retrievedUser = userService.getUserById(user1.getId());

        assertThat(retrievedUser).isNotNull();
        assertThat(retrievedUser.getName()).isEqualTo(user1.getName());
        // 존재하지 않는 사용자 접근 예외 처리 테스트
        assertThatThrownBy(() -> userService.getUserById(10L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("User not found in id: 10");
    }

    @Test
    @DisplayName(value = "사용자 등록")
    void registerUser() {
        UserAddReqDto reqDto = UserAddReqDto.builder()
                .name("NEW USER")
                .height(160)
                .weight(60)
                .gender(0)
                .age(30)
                .build();
        when(userRepository.save(any(User.class))).thenReturn(user1);

        UserResDto createdUser = userService.registerUser(reqDto);

        assertThat(createdUser).isNotNull();
        assertThat(createdUser.getName()).isEqualTo(user1.getName());
    }

    @Test
    @DisplayName(value = "사용자 정보 변경")
    void updateUser() {
        UserUpdateReqDto reqDto = UserUpdateReqDto.builder()
                .height(user1.getHeight() + 5)
                .weight(user1.getWeight() + 5)
                .build();
        when(userRepository.findById(anyLong())).thenReturn((Optional.of(user1)));

        UserResDto updatedUser = userService.updateUser(user1.getId(), reqDto);

        assertThat(updatedUser.getHeight()).isEqualTo(user1.getHeight());
        assertThat(updatedUser.getWeight()).isEqualTo(user1.getWeight());
    }

    @Test
    @DisplayName(value = "사용자 삭제")
    void deleteUser() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user1));
        doNothing().when(userRepository).delete(any(User.class)); // Mock 메서드의 반환값이 없을때

        userService.deleteUser(user1.getId());

        verify(userRepository, times(1)).delete(user1); // delete가 1번 호출 됐는지 검증
    }
}
