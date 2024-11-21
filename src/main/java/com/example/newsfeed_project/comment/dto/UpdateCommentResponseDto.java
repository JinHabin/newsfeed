package com.example.newsfeed_project.comment.dto;

import com.example.newsfeed_project.comment.entity.Comment;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCommentResponseDto {
    @Valid
    @NotBlank(message = "댓글은 필수사항입니다.")
    private String contents;
    private LocalDateTime updatedAt;

    public static UpdateCommentResponseDto toResponseDto(CommentDto commentDto) {
        return UpdateCommentResponseDto.builder()
                .contents(commentDto.getContents())
                .updatedAt(commentDto.getUpdatedAt())
                .build();
    }
}
