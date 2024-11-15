package com.example.scheduler_jpa.service;

import com.example.scheduler_jpa.dto.LoginResponseDto;
import com.example.scheduler_jpa.dto.UserResponseDto;
import com.example.scheduler_jpa.dto.SignUpResponseDto;
import com.example.scheduler_jpa.entity.User;
import com.example.scheduler_jpa.repository.LoginRepository;
import com.example.scheduler_jpa.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final LoginRepository loginRepository;
    //회원가입
    public SignUpResponseDto signUp(String username, String password, String email) {

        User user = new User(username, password, email);

        User savedUser = userRepository.save(user);

        return new SignUpResponseDto(savedUser.getUserId(), savedUser.getUsername(), savedUser.getEmail());
    }

    public UserResponseDto findById(Long userId) {

        Optional<User> optionalUser = userRepository.findById(userId);

        // NPE 방지
        if (optionalUser.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + userId);
        }

        User findUser = optionalUser.get();

        return new UserResponseDto(findUser.getUsername(), findUser.getEmail(), findUser.getCreateAt(), findUser.getUpdateAt());
    }

    public LoginResponseDto login(String password, String email) {
        // 입력받은 email, password와 일치하는 Database 조회
        Long index = loginRepository.findByEmailAndPassword(password, email);

        return new LoginResponseDto(index);
    }
}
