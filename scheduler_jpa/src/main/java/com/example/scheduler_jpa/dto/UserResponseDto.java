package com.example.scheduler_jpa.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserResponseDto {
    private final String username;
    private final String email;
    private final LocalDateTime createAt;
    private final LocalDateTime updateAt;

    public UserResponseDto(String username, String email, LocalDateTime createAt, LocalDateTime updateAt) {
        this.username = getUsername();
        this.email = getEmail();
        this.createAt = getCreateAt();
        this.updateAt = getUpdateAt();
    }
}