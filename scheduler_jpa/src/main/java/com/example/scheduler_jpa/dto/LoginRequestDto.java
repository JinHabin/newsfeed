package com.example.scheduler_jpa.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginRequestDto {
    private String password;
    private String email;
}
