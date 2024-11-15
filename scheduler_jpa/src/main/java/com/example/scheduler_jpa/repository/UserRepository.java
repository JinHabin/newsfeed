package com.example.scheduler_jpa.repository;

import com.example.scheduler_jpa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {


    // 이메일과 비밀번호가 일치하는 작성자 조회
    Optional<User> findEmailAndPassword(String email, String password);
    // 작성자 이름과 일치하는 작성자 조회
    Optional<User> findUserByUsername(String username);

    default User findUserByUsernameOrElseThrow(String username) {
        return findUserByUsername(username).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist username = " + username));
    }
}
