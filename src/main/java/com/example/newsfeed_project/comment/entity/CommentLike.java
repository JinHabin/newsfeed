package com.example.newsfeed_project.comment.entity;

import com.example.newsfeed_project.member.entity.Member;
import com.example.newsfeed_project.newsfeed.entity.Newsfeed;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class CommentLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    public void setMember(Member member) {
        this.member = member;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

}
