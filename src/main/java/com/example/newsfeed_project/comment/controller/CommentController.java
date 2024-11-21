package com.example.newsfeed_project.comment.controller;

import com.example.newsfeed_project.comment.dto.CommentDto;
import com.example.newsfeed_project.comment.dto.UpdateCommentResponseDto;
import com.example.newsfeed_project.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto dto) {
        CommentDto commentDto = commentService.createComment(dto);

        return new ResponseEntity<>(commentDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CommentDto>> findAll() {
        List<CommentDto> commentDtoList = commentService.findAll();

        return new ResponseEntity<>(commentDtoList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentDto> findById(
            @PathVariable Long id) {
        CommentDto commentDto = commentService.findById(id);

        return new ResponseEntity<>(commentDto, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateComment(@PathVariable Long id, @RequestBody CommentDto dto) {
        CommentDto commentDto = commentService.updateComment(id, dto);
        UpdateCommentResponseDto responseDto = UpdateCommentResponseDto.toResponseDto(commentDto);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }
}
