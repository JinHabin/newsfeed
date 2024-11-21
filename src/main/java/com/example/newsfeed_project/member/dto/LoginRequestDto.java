package com.example.newsfeed_project.member.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDto {

    @NotBlank(message = "이메일은 필수 값 입니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수 값 입니다.")
    private String password;
}
