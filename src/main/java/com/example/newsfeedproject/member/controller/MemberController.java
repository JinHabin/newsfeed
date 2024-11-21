package com.example.newsfeedproject.member.controller;

import com.example.newsfeedproject.exception.NoAuthorizedException;
import com.example.newsfeedproject.member.dto.MemberDto;
import com.example.newsfeedproject.member.dto.MemberUpdateResponseDto;
import com.example.newsfeedproject.member.dto.PasswordRequestDto;
import com.example.newsfeedproject.member.service.MemberService;
import com.example.newsfeedproject.util.SessionUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.newsfeedproject.exception.ErrorCode.NO_AUTHOR_PROFILE;

@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }


    @GetMapping("/id")
    public ResponseEntity<?> getProfile(HttpServletRequest request) {
        String email = SessionUtil.validateSession(request.getSession(false));
        MemberDto memberByEmail = memberService.getMemberByEmail(email);
        return ResponseEntity.status(HttpStatus.OK).body(memberByEmail);
    }

    @PostMapping("/register")
    public ResponseEntity<?> createMember(@Valid @RequestBody MemberDto memberDto) {
        MemberDto createdMember = memberService.createMember(memberDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMember);
    }

    @PutMapping("/memberUpdate")
    public ResponseEntity<?> updateMember(@Valid @RequestBody MemberDto memberDto, HttpServletRequest request) {
        String email = SessionUtil.validateSession(request.getSession(false));
        MemberDto existingMember = memberService.getMemberByEmail(email);
        MemberDto updatedMember = memberService.updateMember(existingMember.getId(), memberDto.getPassword(), memberDto);
        MemberUpdateResponseDto responseDto = MemberUpdateResponseDto.toResponseDto(updatedMember);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @GetMapping("/email")
    public ResponseEntity<?> findByEmail(@Valid @RequestParam String email, HttpServletRequest request) {
        String sessionEmail = SessionUtil.validateSession(request.getSession(false));

        if (!sessionEmail.equals(email)) {
            throw new NoAuthorizedException(NO_AUTHOR_PROFILE);
        }

        MemberDto memberByEmail = memberService.getMemberByEmail(email);
        return ResponseEntity.status(HttpStatus.OK).body(memberByEmail);
    }

    @PutMapping("/password/{id}")
    public ResponseEntity<?> changePassword(@Valid @RequestBody PasswordRequestDto passwordRequestDto, HttpSession session) {
        MemberDto memberDto = memberService.changePassword(passwordRequestDto.getOldPassword(), passwordRequestDto.getNewPassword(), session);
        return ResponseEntity.status(HttpStatus.OK).body(memberDto);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteMemberById(HttpServletRequest request) {
        String email = SessionUtil.validateSession(request.getSession(false));
        MemberDto existingMember = memberService.getMemberByEmail(email);
        memberService.deleteMemberById(existingMember.getId());
        return ResponseEntity.status(HttpStatus.OK).body("회원 삭제가 완료되었습니다.");
    }
}
