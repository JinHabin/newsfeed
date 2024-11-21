package com.example.newsfeed_project.friend.controller;

import com.example.newsfeed_project.friend.dto.FriendRequestDto;
import com.example.newsfeed_project.friend.dto.FriendResponseDto;
import com.example.newsfeed_project.friend.service.FriendService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/friends")
public class FriendController {

    private final FriendService friendService;

    public FriendController(FriendService friendService) {this.friendService = friendService;}
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
    @PostMapping
    public ResponseEntity<Object> addFriend(@RequestBody FriendRequestDto friendRequestDto) {
        FriendResponseDto response = friendService.addFriend(friendRequestDto);
        return ResponseEntity.ok(response); // 요청 성공시 응답 반환
    }
    //친구 승인
    @PostMapping("/response/{requestId}")
    public ResponseEntity<Object> acceptFriendApproval(@PathVariable Long requestId, @RequestParam boolean friendApproval) {
        FriendResponseDto acceptRequest = friendService.acceptFriendApproval(requestId, friendApproval);
        return ResponseEntity.ok(acceptRequest);
    }
    // 친구 삭제
    @DeleteMapping("/{requestId}")
    public ResponseEntity<Object> deleteFriend(
            @PathVariable Long requestId,
            HttpServletRequest req) {
            friendService.deleteFriend(requestId, req);
            return ResponseEntity.ok("친구가 삭제되었습니다.");
        }
    }

