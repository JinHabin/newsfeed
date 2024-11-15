package com.example.scheduler_jpa.dto;

import lombok.Getter;

@Getter
public class SignUpResponseDto {
    private final Long userId;

    private final String username;

    private final String email;

    public SignUpResponseDto(Long userId, String username, String email) {
        this.userId = userId;
        this.username = username;
        this.email = email;
    }
}
