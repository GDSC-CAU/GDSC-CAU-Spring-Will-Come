package cau.gdsc.service;

import cau.gdsc.domain.User;
import cau.gdsc.dto.UserAddReqDto;
import cau.gdsc.dto.UserUpdateReqDto;
import cau.gdsc.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
//    private final UserMapper userMapper;

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No User"));
    }

    @Transactional
    public User registerUser(UserAddReqDto reqDto) {
        return userRepository.save(reqDto.toEntity());
    }

    @Transactional // JPA dirty checking을 이용한 update
    public User updateUser(Long id, UserUpdateReqDto reqDto){
        User target = getUserById(id);
        target.update(reqDto.getHeight(), reqDto.getWeight());
        return target;
    }

    @Transactional
    public void deleteUser(Long id) {
        User target = getUserById(id);
        userRepository.delete(target);
    }
}
