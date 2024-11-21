package com.example.newsfeed_project.comment.dto;

import com.example.newsfeed_project.comment.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCommentResponseDto {
    private String contents;

    public static UpdateCommentResponseDto updateDto(Comment comment) {
        return UpdateCommentResponseDto.builder()
                .contents(comment.getContents())
                .build();
    }

    public static UpdateCommentResponseDto toResponseDto(CommentDto commentDto) {
        return UpdateCommentResponseDto.builder()
                .contents(commentDto.getContents())
                .build();
    }


}
