package com.example.newsfeed_project.comment.dto;

import com.example.newsfeed_project.comment.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
public class CommentDto {

    private  Long id;
    private  String contents;
    private  LocalDateTime createdAt;

    public static CommentDto toDto(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .contents(comment.getContents())
                .createdAt(LocalDateTime.now())
                .build();
    }
}
