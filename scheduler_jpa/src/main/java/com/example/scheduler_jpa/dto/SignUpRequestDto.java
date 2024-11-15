package com.example.scheduler_jpa.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.processing.Pattern;

@Getter
public class SignUpRequestDto {
    private Long id;

    private final String username;

    private final String password;

    private final String email;

    public SignUpRequestDto(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
}

