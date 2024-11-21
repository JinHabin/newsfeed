package com.example.newsfeed_project.friend.repository;

import com.example.newsfeed_project.friend.entity.Friend;
import com.example.newsfeed_project.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FriendRepository extends JpaRepository<Friend, Long> {
    Page<Friend> findByRequestFriendIdAndFriendApproval(Long id, boolean b, Pageable pageable);

    boolean existsByRequestFriendAndResponseFriend(Member requestFriend, Member responseFriend);

    Optional<Friend> findByIdAndStatus(Long id, String status);
    Optional<Friend> findByRequestFriendAndResponseFriend(Member requestFriend, Member responseFriend);
    //Page<Friend> findByFriendApprovalAndRequestFriendIdOrResponseFriendId(boolean friendApproval, Member requestFriend, Member responseFriend, Pageable pageable);
    //Page<Friend> findFriends(Long memberId, Long friendId , boolean friendApproval, Pageable pageable);
}
