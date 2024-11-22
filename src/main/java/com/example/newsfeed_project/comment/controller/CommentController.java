package com.example.newsfeed_project.comment.controller;

import com.example.newsfeed_project.comment.dto.CommentRequestDto;
import com.example.newsfeed_project.comment.dto.CommentResponseDto;
import com.example.newsfeed_project.comment.dto.UpdateCommentResponseDto;
import com.example.newsfeed_project.comment.service.CommentService;
import com.example.newsfeed_project.util.SessionUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
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
    public ResponseEntity<CommentResponseDto> createComment(
            @PathVariable Long newsfeedId,
            @Valid @RequestBody CommentRequestDto dto,
            HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        CommentResponseDto commentResponseDto = commentService.createComment(newsfeedId, dto, SessionUtil.validateSession(session));
        return new ResponseEntity<>(commentResponseDto, HttpStatus.CREATED);
    }

    //댓글 전체 조회
    @GetMapping("/newsfeed/{newsfeedId}")
    public ResponseEntity<List<CommentResponseDto>> findAll(
            @PathVariable Long newsfeedId,
            @PageableDefault(size = 10,sort = "updatedAt", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        List<CommentResponseDto> commentResponseDtoList = commentService.findAll(newsfeedId, pageable);
        return new ResponseEntity<>(commentResponseDtoList, HttpStatus.OK);
    }

    //댓글 단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<CommentResponseDto> findById(
            @PathVariable Long id) {
        CommentResponseDto commentResponseDto = commentService.findById(id);
        return new ResponseEntity<>(commentResponseDto, HttpStatus.OK);
    }

    //댓글 수정
    @PutMapping("/{id}")
    public ResponseEntity<?> updateComment(
            @PathVariable Long id,
            @Valid @RequestBody CommentRequestDto dto,
            HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        CommentResponseDto commentResponseDto = commentService.updateComment(id, dto, SessionUtil.validateSession(session));
        UpdateCommentResponseDto responseDto = UpdateCommentResponseDto.toResponseDto(commentResponseDto);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    //댓글 삭제
    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteComment(
            @PathVariable Long commentId,
            HttpServletRequest request){
        HttpSession session = request.getSession(false);
        commentService.deleteComment(commentId, SessionUtil.validateSession(session));

        return ResponseEntity.status(HttpStatus.OK).body("댓글 삭제가 되었습니다.");
    }
}
