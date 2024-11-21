package com.example.newsfeed_project.friend.service;

import com.example.newsfeed_project.friend.dto.FriendRequestDto;
import com.example.newsfeed_project.friend.dto.FriendResponseDto;
import com.example.newsfeed_project.friend.entity.Friend;
import com.example.newsfeed_project.friend.repository.FriendRepository;
import com.example.newsfeed_project.member.entity.Member;
import com.example.newsfeed_project.member.repository.MemberRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class FriendServiceImpl implements FriendService {

    private final FriendRepository friendRepository;
    private final MemberRepository memberRepository;

    public FriendServiceImpl(FriendRepository friendRepository, MemberRepository memberRepository) {
        this.friendRepository = friendRepository;
        this.memberRepository = memberRepository;
    }

    // 친구 요청 생성 및 상태 설정
    public FriendResponseDto addFriend(FriendRequestDto friendRequestDto) {
        Member requestFriend = memberRepository.findById(friendRequestDto.getRequestFriendId())
                .orElseThrow(() -> new RuntimeException("요청하는 사용자를 찾을 수 없습니다."));
        Member responseFriend = memberRepository.findById(friendRequestDto.getResponseFriendId())
                .orElseThrow(() -> new RuntimeException("요청 받는 사용자를 찾을 수 없습니다."));

        // 이미 요청이 존재하는지 확인

        Friend friendRequest = Friend.builder()
                .requestFriendId(requestFriend.getId())
                .responseFriendId(responseFriend.getId())
                .friendApproval(false)
                .build();
        friendRepository.save(friendRequest); // 친구 요청 저장

        return friendRequest.toDto();
    }

    //  친구 요청 승인
    public FriendResponseDto acceptFriendApproval(Long requestId, boolean friendApproval) {
        Friend friendRequest = friendRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("친구 요청을 찾을 수 없습니다."));

        friendRequest.setFriendApproval(friendApproval);

        friendRepository.save(friendRequest); // 변경된 요청 저장
        return friendRequest.toDto(); //변환하여 반환
    }
/*
    //친구 전체 조회
    public List<FriendDto> getFriendList(int page, int size, HttpServletRequest request) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));

        Member member = (Member) request.getAttribute("member");
        // true인 친구 목록 조회
        Page<Friend> friends = friendRepository.findFriends(member.getId(), member.getId(), true, pageable);
        // 친구 id 목록
        List<Long> friendIds = new ArrayList<>();

        for (Friend friend : friends) {
            if (member.getId().equals(friend.getRequestFriendId())) {
                friend.setFriendApproval(true);
                //friendIds.add(friend.getResponseFriendId());
            }
            else {
                friend.setFriendApproval(false);
                //friendIds.add(friend.getRequestFriendId());
            }

        }

//        List<Long> friendIds = friends.stream()
//                .map(friend -> friend.getRequestFriendId().equals(member.getId()) ? friend.getRequestFriendId() : friend.getResponseFriendId())
//                .collect(Collectors.toList());

        // 추출해낸 친구 id 목록으로 해당하는 멤버들 정보 조회

        //member 컬럼 중에서 memberId, String image,  String email, String name 을 포함하는 리스트 반환

    }

*/
    // 친구 삭제
    public void deleteFriend(Long requestId, HttpServletRequest req) {
        Member member = (Member) req.getAttribute("member");

        Friend friend = friendRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("친구 요청을 찾을 수 없습니다."));

        if (!friend.getRequestFriendId().equals(member.getId()) && !friend.getResponseFriendId().equals(member.getId())) {
            throw new IllegalArgumentException("이 친구 요청을 삭제할 권한이 없습니다.");
        }

        friendRepository.delete(friend);
    }
}
