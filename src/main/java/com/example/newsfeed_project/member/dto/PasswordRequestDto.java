package com.example.newsfeed_project.member.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PasswordRequestDto {

    @NotBlank(message = "비밀번호는 필수 값 입니다.")
    private String oldPassword;

    @NotBlank(message = "신규 비밀번호는 필수 값 입니다.")
    private String newPassword;
}
