package com.example.scheduler_jpa.repository;

import com.example.scheduler_jpa.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public class LoginRepository {

    private final UserRepository userRepository;

    public LoginRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Long findByEmailAndPassword(String email, String password) {
        return userRepository.findEmailAndPassword(email, password)
                .map(User::getUserId)
                .orElse(null);
    }
}