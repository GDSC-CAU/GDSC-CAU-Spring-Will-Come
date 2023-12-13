package cau.gdsc.spring.repository;

import cau.gdsc.domain.User;
import cau.gdsc.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@Transactional
@DataJpaTest // jpa 관련 테스트: Jpa 관련 컴포넌트만
@ActiveProfiles("test") // application의 test 프로필 사용
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setup() {
        User user1 = User.builder().name("NEW USER").height(160).weight(60).gender(0).age(30).build();
        System.out.println(user1.getName());
        User user2 = User.builder().name("SECONDARY").height(185).weight(75).gender(1).age(28).build();
        List<User> users = userRepository.saveAll(List.of(user1, user2));
        users.stream().map(User::getName).forEach(System.out::println);
    }

    @AfterEach
    void after() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName(value = "모든 사용자 조회")
    void listAllUserTest() {
        List<User> users = userRepository.findAll();
        assertThat(users.size()).isEqualTo(2);
    }

    @Test
    @DisplayName(value = "특정 사용자 조회")
    void listUserTest() {
//        userRepository.findAll().stream().map(User::getName).forEach(System.out::println);
        User user = userRepository.findAll().get(0); // GenerationType.IDENTITY로 인해 새로운 레코드의 PK != 현재 레코드 수이므로 인덱스로 접근
        assertThat(user.getName()).isEqualTo("NEW USER");
    }

    @Test
    @DisplayName(value = "사용자 정보 변경")
    void updateUserTest() {
        User user = userRepository.findAll().get(0);

        user.update(160, 55);
        User updatedUser = userRepository.save(user);
        assertThat(user.getHeight()).isEqualTo(160);
        assertThat(user.getWeight()).isEqualTo(55);
    }

    @Test
    @DisplayName(value = "사용자 삭제")
    void deleteUserTest() {
        User user = userRepository.findAll().get(0);
        userRepository.delete(user);
        Optional<User> deletedUser = userRepository.findById(user.getId());
        assertThat(deletedUser).isEmpty();
    }
}
