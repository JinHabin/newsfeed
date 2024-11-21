package com.example.newsfeed_project.friend.service;

import com.example.newsfeed_project.friend.dto.FriendRequestDto;
import com.example.newsfeed_project.friend.dto.FriendResponseDto;
import jakarta.servlet.http.HttpServletRequest;

public interface FriendService {
    FriendResponseDto addFriend(FriendRequestDto friendRequestDto);
    FriendResponseDto acceptFriendApproval(Long requestId, boolean friendApproval);
    //List<FriendDto> getFriendList(int page, int size, HttpServletRequest request);
    void deleteFriend(Long requestId, HttpServletRequest req);
}
