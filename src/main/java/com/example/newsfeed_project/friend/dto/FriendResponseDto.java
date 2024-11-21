package com.example.newsfeed_project.friend.dto;

import com.example.newsfeed_project.friend.entity.Friend;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class FriendResponseDto {
    private Long id;
    private boolean friendApproval;

    public FriendResponseDto(Friend friend) {
        this.id = friend.getId();
        this.friendApproval = friend.isFriendApproval();
    }
}
