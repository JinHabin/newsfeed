package com.example.scheduler_jpa.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 사용자가 등록할 제목, 내용, 담당자, 비밀번호, 작성일
 *
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleRequestDto {
    private String title;
    private String contents;
    private String username;
    private String password;
}