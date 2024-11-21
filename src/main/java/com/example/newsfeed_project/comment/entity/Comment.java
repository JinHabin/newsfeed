package com.example.newsfeed_project.comment.entity;

import com.example.newsfeed_project.comment.dto.CommentDto;
import com.example.newsfeed_project.comment.dto.UpdateCommentResponseDto;
import com.example.newsfeed_project.common.BaseEntity;
import com.example.newsfeed_project.member.dto.MemberDto;
import com.example.newsfeed_project.member.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Comment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member memberId;

    private Long feedId;
    private String contents;

    public static Comment toEntity(CommentDto dto) {
        return Comment.builder()
                .id(dto.getId())
                .contents(dto.getContents())
                .build();
    }

    public void updateComment(CommentDto dto) {
        if (dto.getContents() != null) {
            this.contents = dto.getContents();
        }
    }


}
