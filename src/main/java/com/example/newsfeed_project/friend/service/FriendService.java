package com.example.newsfeed_project.friend.service;

import com.example.newsfeed_project.friend.dto.FriendDto;
import com.example.newsfeed_project.friend.dto.FriendRequestDto;
import com.example.newsfeed_project.friend.dto.FriendResponseDto;
import com.example.newsfeed_project.friend.entity.Friend;
import com.example.newsfeed_project.member.dto.MemberDto;
import com.example.newsfeed_project.member.entity.Member;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public interface FriendService {
    void sendFriendRequest(FriendDto friendDto, String loggedInUserEmail);
    FriendDto acceptFriendRequest(Long requestId, boolean isApproved, String loggedInUserEmail);
    //List<FriendDto> getFriendList(int page, int size, HttpServletRequest request);
    void deleteFriend(Long requestId, MemberDto memberDto);
}
