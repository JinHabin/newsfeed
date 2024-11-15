package com.example.scheduler_jpa.controller;

import com.example.scheduler_jpa.dto.ScheduleRequestDto;
import com.example.scheduler_jpa.dto.ScheduleResponseDto;
import com.example.scheduler_jpa.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    // 일정 등록
    @PostMapping
    public ResponseEntity<ScheduleResponseDto> createSchedule(@RequestBody ScheduleRequestDto requestDto) {

            ScheduleResponseDto scheduleResponseDto =
                    scheduleService.save(
                            requestDto.getTitle(),
                            requestDto.getContents(),
                            requestDto.getUsername(),
                            requestDto.getPassword()
                    );
            return new ResponseEntity<>(scheduleResponseDto, HttpStatus.CREATED);
    }
    // 선택 일정 조회
    @GetMapping("/{scheduleId}")
    public ResponseEntity<ScheduleResponseDto> findScheduleById(@PathVariable Long scheduleId) {
        ScheduleResponseDto scheduleResponseDto = scheduleService.findScheduleById(scheduleId);
        return new ResponseEntity<>(scheduleResponseDto, HttpStatus.OK);
    }

    // 전체 일정 조회
    @GetMapping
    public ResponseEntity<List<ScheduleResponseDto>> findAllSchedule() {
        List<ScheduleResponseDto> scheduleResponseDtoList = scheduleService.findAllSchedule();
        return new ResponseEntity<>(scheduleResponseDtoList, HttpStatus.OK);
    }

    // 선택 일정 수정
    @PutMapping("/{scheduleId}")
    public ScheduleResponseDto updateSchedule(@PathVariable Long scheduleId, @RequestBody ScheduleRequestDto requestDto) {
        return scheduleService.updateSchedule(scheduleId, requestDto, requestDto.getPassword());
    }

    // 선택 일정 삭제 O
    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long scheduleId, @RequestBody ScheduleRequestDto requestDto) {
        scheduleService.deleteSchedule(scheduleId, requestDto.getPassword());
        return new ResponseEntity<>(HttpStatus.OK);
    }

}