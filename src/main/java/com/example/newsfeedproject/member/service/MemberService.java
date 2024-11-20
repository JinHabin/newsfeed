package com.example.newsfeedproject.member.service;

import com.example.newsfeedproject.member.dto.MemberDto;
import com.example.newsfeedproject.member.entity.Member;
import jakarta.servlet.http.HttpSession;

public interface MemberService {
    MemberDto createMember(MemberDto memberDto);
    MemberDto updateMember(Long id, String password, MemberDto memberDto);
    MemberDto getMemberById(Long id);
    MemberDto getMemberByEmail(String email);
    void deleteMemberById(Long id);
    MemberDto changePassword(String oldPassword, String newPassword, HttpSession session);
    Member validateId(Long id);
    Member validateEmail(String email);
    boolean authenticate(String email, String password);
}
