package cau.gdsc.service;

import cau.gdsc.domain.User;
import cau.gdsc.dto.user.UserResDto;
import cau.gdsc.dto.user.UserUpdateReqDto;
import cau.gdsc.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
//    private final UserMapper userMapper;

    public List<UserResDto> getUsers() {
        return userRepository
                .findAll()
                .stream()
                .map(UserResDto::of)
                .collect(Collectors.toList());
    }

    public UserResDto getUserById(Long id) {
        User retrievedUser = findUserById(id);
        return UserResDto.of(retrievedUser);
    }

//    @Transactional
//    public UserResDto registerUser(UserAddReqDto reqDto) {
//        User newUser = userRepository.save(reqDto.toEntity());
//        return UserResDto.of(newUser);
//    }

    @Transactional // JPA dirty checking을 이용한 update
    public UserResDto updateUser(UserUpdateReqDto reqDto){
        User target = findUserById(reqDto.getUserId());
        target.update(reqDto.getHeight(), reqDto.getWeight());
        return UserResDto.of(target);
    }

    @Transactional
    public void deleteUser(Long id) {
        User target = findUserById(id);
        userRepository.delete(target);
    }

    private User findUserById(Long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found in id: " + id));
    }
}
