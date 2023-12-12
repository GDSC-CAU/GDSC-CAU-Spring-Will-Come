package cau.gdsc.spring.repository;

import cau.gdsc.domain.User;
import cau.gdsc.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest // jpa 관련 테스트: Jpa 관련 컴포넌트만
@ActiveProfiles("test") // application의 test 프로필 사용
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setup(){
        User user1 = User.createUser("NEW USER", 160, 60, 0, 30);
        User user2 = User.createUser("SECONDARY", 185, 75, 1, 28);
        userRepository.saveAll(List.of(user1, user2));
    }

    @AfterEach
    void after(){
        userRepository.deleteAll();
    }

    @Test
    @DisplayName(value = "모든 사용자 조회")
    void listAllUserTest(){
        List<User> users = userRepository.findAll();
        assertThat(users.size()).isEqualTo(2);
    }

    @Test
    @DisplayName(value = "특정 사용자 조회")
    void listUserTest(){
        User user = userRepository.findAll().get(0); // GenerationType.IDENTITY로 인해 새로운 레코드의 PK != 현재 레코드 수이므로 인덱스로 접근
        assertThat(user.getName()).isEqualTo("NEW USER");
    }

    @Test
    @DisplayName(value = "사용자 정보 변경")
    void updateUserTest(){
        User user = userRepository.findAll().get(0);

        user.update(160, 55);
        User updatedUser = userRepository.save(user);
        assertThat(user.getHeight()).isEqualTo(160);
        assertThat(user.getWeight()).isEqualTo(55);
    }

    @Test
    @DisplayName(value = "사용자 삭제")
    void deleteUserTest(){
        User user = userRepository.findAll().get(0);
        userRepository.deleteById(user.getId());
        Optional<User> deletedUser = userRepository.findById(user.getId());
        assertThat(deletedUser).isEmpty();
    }
}
