package com.example.newsfeed_project.friend.entity;

import com.example.newsfeed_project.friend.dto.FriendResponseDto;
import com.example.newsfeed_project.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Friend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean friendApproval;
    private Long requestFriendId;
    private Long responseFriendId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    //생성자
    public Friend(Member member) {
        this.member = member;
    }

    public FriendResponseDto toDto() {
        return new FriendResponseDto (
                id,
                friendApproval
        );
    }


}
