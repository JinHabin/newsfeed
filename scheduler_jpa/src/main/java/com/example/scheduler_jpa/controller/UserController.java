package com.example.scheduler_jpa.controller;

import com.example.scheduler_jpa.dto.LoginRequestDto;
import com.example.scheduler_jpa.dto.LoginResponseDto;
import com.example.scheduler_jpa.dto.SignUpRequestDto;
import com.example.scheduler_jpa.dto.SignUpResponseDto;
import com.example.scheduler_jpa.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.Cookie;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user/signup")
    public ResponseEntity<SignUpResponseDto> signUp(@RequestBody SignUpRequestDto requestDto) {
        SignUpResponseDto signUpResponseDto =
                userService.signUp(
                        requestDto.getUsername(),
                        requestDto.getPassword(),
                        requestDto.getEmail()
                );

        return new ResponseEntity<>(signUpResponseDto, HttpStatus.CREATED);
    }


    @PostMapping("/user/login")
    public String login(
            @Valid @ModelAttribute LoginRequestDto request,
            HttpServletResponse response // 쿠키값 세팅에 필요
    ) {
        // 로그인 유저 조회
        LoginResponseDto responseDto = userService.login(request.getPassword(), request.getEmail());

        if (responseDto.getUserId() == null) {
            // 로그인 실패 예외처리
            return "login";
        }

        // 로그인 성공 처리
        Cookie cookie = new Cookie("userId", String.valueOf(responseDto.getUserId()));

        response.addCookie(cookie);

        // home 페이지로 리다이렉트
        return "redirect:/home";
    }

    @PostMapping("/user/logout")
    public String logout(
            HttpServletResponse response
    ) {
        Cookie cookie = new Cookie("userId", null);
        // 0초로 쿠키를 세팅하여 사라지게 만듬
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        // api 페이지로 리다이렉트
        return "redirect:/api";
    }

}