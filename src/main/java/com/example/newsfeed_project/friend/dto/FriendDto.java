package com.example.newsfeed_project.friend.dto;

import com.example.newsfeed_project.friend.entity.Friend;
import com.example.newsfeed_project.member.dto.MemberDto;
import lombok.*;


@Builder
@Getter
@Setter
@AllArgsConstructor
public class FriendDto {
    private Long memberId;
    private String image;
    private String email;
    private String name;
    private MemberDto member;

    public FriendDto(Friend friend) {
        this.memberId = friend.getMember().getId();
        this.image = friend.getMember().getImage();
        this.email = friend.getMember().getEmail();
        this.name = friend.getMember().getName();
    }
}