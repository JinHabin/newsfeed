package com.example.newsfeed_project.comment.controller;

import com.example.newsfeed_project.comment.dto.CommentDto;
import com.example.newsfeed_project.comment.dto.UpdateCommentResponseDto;
import com.example.newsfeed_project.comment.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    //댓글 생성
    @PostMapping("/{newsfeedId}")
    public ResponseEntity<CommentDto> createComment(
            @PathVariable Long newsfeedId,
            @RequestBody CommentDto dto,
            HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        CommentDto commentDto = commentService.createComment(newsfeedId, dto, session);
        return new ResponseEntity<>(commentDto, HttpStatus.CREATED);
    }

    //댓글 전체 조회
    @GetMapping
    public ResponseEntity<List<CommentDto>> findAll(
            @PageableDefault(size = 10, sort = "updatedAt", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        List<CommentDto> commentDtoList = commentService.findAll();

        return new ResponseEntity<>(commentDtoList, HttpStatus.OK);
    }

    //댓글 단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<CommentDto> findById(
            @PathVariable Long id) {
        CommentDto commentDto = commentService.findById(id);

        return new ResponseEntity<>(commentDto, HttpStatus.OK);
    }

    //댓글 수정
    @PutMapping("/{id}")
    public ResponseEntity<?> updateComment(@PathVariable Long id, @RequestBody CommentDto dto) {
        CommentDto commentDto = commentService.updateComment(id, dto);
        UpdateCommentResponseDto responseDto = UpdateCommentResponseDto.toResponseDto(commentDto);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    //댓글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteComment(
            @PathVariable Long id){

        commentService.deleteComment(id);

        return ResponseEntity.status(HttpStatus.OK).body("댓글 삭제가 되었습니다.");
    }


}
