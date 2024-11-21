package com.example.newsfeed_project.comment.service;

import com.example.newsfeed_project.comment.dto.CommentDto;
import com.example.newsfeed_project.comment.dto.UpdateCommentResponseDto;
import com.example.newsfeed_project.comment.entity.Comment;
import com.example.newsfeed_project.comment.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public CommentDto createComment(CommentDto dto) {
        Comment createComment = Comment.toEntity(dto);
        Comment save = commentRepository.save(createComment);

        return CommentDto.toDto(save);
    }


    public List<CommentDto> findAll() {
        return commentRepository.findAll().stream().map(CommentDto::toDto).toList();
    }

    public CommentDto findById(Long id) {
        Comment findId = commentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("댓글을 찾지 못했습니다."));

        return new CommentDto(findId.getId(), findId.getContents(),findId.getCreatedAt());
    }

    public CommentDto updateComment(Long id, CommentDto dto) {
        Comment findId = commentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("댓글을 찾지 못했습니다."));

        findId.updateComment(dto);
        Comment save = commentRepository.save(findId);

        return CommentDto.toDto(save);
    }
}
