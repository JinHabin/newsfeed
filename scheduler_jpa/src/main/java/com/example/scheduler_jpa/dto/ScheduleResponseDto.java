package com.example.scheduler_jpa.dto;

import com.example.scheduler_jpa.entity.Schedule;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ScheduleResponseDto {
    private Long id;
    private String title;
    private String contents;
    private String username;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public ScheduleResponseDto(Schedule schedule) {
        this.id = schedule.getId();
        this.title = schedule.getTitle();
        this.contents = schedule.getContents();
        this.username = schedule.getUser().getUsername();
        this.createdAt = schedule.getCreateAt();
        this.modifiedAt = schedule.getUpdateAt();
    }
}