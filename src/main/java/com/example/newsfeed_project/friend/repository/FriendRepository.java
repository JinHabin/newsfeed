package com.example.newsfeed_project.friend.repository;

import com.example.newsfeed_project.friend.entity.Friend;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendRepository extends JpaRepository<Friend, Long> {
    //Page<Friend> findByFriendApprovalAndRequestFriendIdOrResponseFriendId(boolean friendApproval, Member requestFriend, Member responseFriend, Pageable pageable);
    //Page<Friend> findFriends(Long memberId, Long friendId , boolean friendApproval, Pageable pageable);

}
