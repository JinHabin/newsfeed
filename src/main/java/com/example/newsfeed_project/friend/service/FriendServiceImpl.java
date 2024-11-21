package com.example.newsfeed_project.friend.service;

import com.example.newsfeed_project.config.PasswordEncoder;
import com.example.newsfeed_project.friend.dto.FriendDto;
import com.example.newsfeed_project.friend.entity.Friend;
import com.example.newsfeed_project.friend.repository.FriendRepository;
import com.example.newsfeed_project.member.dto.MemberDto;
import com.example.newsfeed_project.member.entity.Member;
import com.example.newsfeed_project.member.repository.MemberRepository;
import com.example.newsfeed_project.member.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Slf4j
@Service
public class FriendServiceImpl implements FriendService {

    private final FriendRepository friendRepository;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public FriendServiceImpl(FriendRepository friendRepository, MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.friendRepository = friendRepository;
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void sendFriendRequest(FriendDto friendDto, String loggedInUserEmail) {

        if (loggedInUserEmail == null) {
            throw new IllegalArgumentException("로그인된 사용자 정보가 누락되었습니다.");
        }

        // 요청 및 응답 친구 ID로 멤버 조회
        Member requestFriend = memberRepository.findByEmail(loggedInUserEmail)
                .orElseThrow(() -> new IllegalArgumentException("로그인된 사용자를 찾을 수 없습니다."));

        Member responseFriend = memberRepository.findById(friendDto.getResponseFriendId())
                .orElseThrow(() -> new IllegalArgumentException("Response friend not found."));

        if (!requestFriend.getId().equals(friendDto.getRequestFriendId())) {
            throw new IllegalArgumentException("로그인된 사용자와 요청자가 일치하지 않습니다.");
        }

        if (requestFriend.getId().equals(responseFriend.getId())) {
            throw new IllegalArgumentException("자기 자신에게 친구 요청을 보낼 수 없습니다.");
        }

        Optional<Friend> existingRequest = friendRepository.findByRequestFriendAndResponseFriend(requestFriend, responseFriend);

        if (existingRequest.isPresent()) {
            Friend friend = existingRequest.get();
            if ("REJECTED".equals(friend.getStatus())) {
                friend.setStatus("PENDING");
                friend.setUpdatedAt(LocalDateTime.now());
                friendRepository.save(friend); // 변경 사항 저장
                log.info("거부된 요청을 새로 보냄: RequestFriendId={}, ResponseFriendId={}",
                        requestFriend.getId(), responseFriend.getId());
                return;
            }
            if ("APPROVED".equals(friend.getStatus())) {
                throw new IllegalArgumentException("이미 친구 상태입니다.");
            }
            if ("PENDING".equals(friend.getStatus())) {
                throw new IllegalArgumentException("이미 친구 요청을 보냈습니다.");
            }
        }

        Friend friend = Friend.builder()
                .requestFriend(requestFriend)
                .responseFriend(responseFriend)
                .status("PENDING")
                .build();
        friendRepository.save(friend);

        log.info("새로운 친구 요청 보냄: RequestFriendId={}, ResponseFriendId={}",
                requestFriend.getId(), responseFriend.getId());
    }

    @Override
    @Transactional
    public FriendDto acceptFriendRequest(Long requestId, boolean isApproved, String loggedInUserEmail) {
        // 요청된 친구 데이터를 조회 (상태와 ID로 검색)
        Friend friendRequest = friendRepository.findByIdAndStatus(requestId, "PENDING")
                .orElseThrow(() -> new IllegalArgumentException("친구 요청을 찾을 수 없습니다."));

        // 현재 로그인한 사용자가 응답자인지 확인
        if (!friendRequest.getResponseFriend().getEmail().equals(loggedInUserEmail)) {
            throw new IllegalArgumentException("승인/거부 권한이 없습니다.");
        }

        if (isApproved) {
            // 승인 처리
            friendRequest.approve();
            friendRepository.save(friendRequest);
            log.info("친구 요청 승인됨: RequestId={}, ResponseFriendEmail={}", requestId, loggedInUserEmail);
            return new FriendDto(friendRequest);
        } else {
            // 거부 처리 (상태를 REJECTED로 변경)
            friendRequest.setStatus("REJECTED");
            friendRepository.save(friendRequest);
            log.info("친구 요청 거부됨: RequestId={}, ResponseFriendEmail={}", requestId, loggedInUserEmail);
            return FriendDto.builder()
                    .id(requestId)
                    .name("요청 거부됨")
                    .build();
        }
    }

//    public Page<FriendDto> getFriendList(int page, int size, Member member) {
//        // 페이지 크기를 10으로 제한
//        size = Math.min(size, 10);
//        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
//
//        // 승인된 친구 목록 조회
//        Page<Friend> friends = friendRepository.findByRequestFriendIdAndFriendApproval(member.getId(), true, pageable);
//
//        // FriendDto 리스트 변환
//        List<FriendDto> friendDtos = friends.stream()
//                .map(friend -> {
//                    // 요청자와 응답자 중 상대방의 정보를 가져오기
//                    Member relatedUser = member.getId().equals(friend.getRequestFriend().getId())
//                            ? friend.getResponseFriend()
//                            : friend.getRequestFriend();
//
//                    return FriendDto.builder()
//                            .id(friend.getId())
//                            .requestFriendId(friend.getRequestFriend().getId())
//                            .responseFriendId(friend.getResponseFriend().getId())
//                            .image(relatedUser.getImage())
//                            .email(relatedUser.getEmail())
//                            .name(relatedUser.getName())
//                            .isApproval(friend.isApproval())
//                            .build();
//                })
//                .toList();
//
//        // Page 객체 생성
//        return new PageImpl<>(friendDtos, pageable, friends.getTotalElements());
//    }

    // 친구 삭제
    public void deleteFriend(Long requestId, MemberDto memberDto) {
        Friend friend = friendRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("친구 요청을 찾을 수 없습니다."));

        // 삭제 요청자가 해당 친구 관계의 소유자인지 확인
        if (!friend.getMember().getId().equals(memberDto.getId())) {
            throw new IllegalArgumentException("해당 친구 관계를 삭제할 권한이 없습니다.");
        }

        // 비밀번호 검증
        if (!passwordEncoder.matches(memberDto.getPassword(), friend.getMember().getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        friendRepository.delete(friend);
    }

    private boolean determineApprovalLogic(Member requestFriend, Member responseFriend) {
        // 자기 자신에게 친구 요청을 보낼 수 없음
        if (requestFriend.getId().equals(responseFriend.getId())) {
            throw new IllegalArgumentException("자기 자신에게 친구 요청을 보낼 수 없습니다.");
        }

        // 이미 친구 관계인지 확인
        boolean alreadyFriends = friendRepository.existsByRequestFriendAndResponseFriend(requestFriend, responseFriend);
        if (alreadyFriends) {
            throw new IllegalArgumentException("이미 친구 관계입니다.");
        }

        // 기본적으로 승인 상태 반환
        return true; // 변경 필요
    }
}
