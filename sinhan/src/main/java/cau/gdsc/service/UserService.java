package cau.gdsc.service;

import cau.gdsc.domain.User;
import cau.gdsc.dto.UserAddReqDto;
//import cau.gdsc.mapper.UserMapper;
import cau.gdsc.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
//    private final UserMapper userMapper;

    public List<User> getUsers(){
        return userRepository.findAll();
    }

    public User getUserById(Long id){
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("No User"));
    }

//    public User registerUser(UserAddReqDto reqDto){
//
//    }
}
