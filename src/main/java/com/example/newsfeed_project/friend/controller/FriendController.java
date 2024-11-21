package com.example.newsfeed_project.friend.controller;

import com.example.newsfeed_project.friend.dto.FriendDto;
import com.example.newsfeed_project.friend.service.FriendService;
import com.example.newsfeed_project.member.dto.MemberDto;
import com.example.newsfeed_project.util.SessionUtil;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/friends")
public class FriendController {

    private final FriendService friendService;

    public FriendController(FriendService friendService) {
        this.friendService = friendService;
    }

    /*
    //친구 리스트 조회
    @GetMapping()
    public ResponseEntity<Object> getFriendList(@RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "10") int size,
                                               HttpServletRequest request) {
        List<FriendDto> response = friendService.getFriendList(page, size, request);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
*/
    // 친구 요청 생성
    @PostMapping("/request")
    public ResponseEntity<String> sendFriendRequest(@RequestBody FriendDto friendDto, HttpSession session) {
        String loggedInUserEmail = SessionUtil.validateSession(session);
        friendService.sendFriendRequest(friendDto, loggedInUserEmail);
        return ResponseEntity.ok("친구 요청이 성공적으로 보내졌습니다.");
    }

    // 친구 요청 승인/거부 API
    @PatchMapping("/accept/{requestId}")
    public ResponseEntity<String> acceptFriendRequest(
            @PathVariable Long requestId,
            @RequestParam boolean isApproved,
            HttpSession session) {

        String loggedInUserEmail = SessionUtil.validateSession(session);
        FriendDto response = friendService.acceptFriendRequest(requestId, isApproved, loggedInUserEmail);
        return ResponseEntity.ok(isApproved ? "친구 요청이 승인되었습니다." : "친구 요청이 거부되었습니다.");
    }

    // 친구 삭제
    @DeleteMapping("/{requestId}")
    public ResponseEntity<?> deleteFriend(
            @PathVariable Long requestId, @RequestBody MemberDto memberDto) {
        friendService.deleteFriend(requestId, memberDto);
        return ResponseEntity.status(HttpStatus.OK).body("친구가 성고적으로 삭제 되었습니다.");
    }
}

