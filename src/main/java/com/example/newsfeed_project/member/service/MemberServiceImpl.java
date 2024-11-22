package com.example.newsfeed_project.member.service;

import static com.example.newsfeed_project.exception.ErrorCode.EMAIL_EXIST;
import static com.example.newsfeed_project.exception.ErrorCode.NOT_FOUND_EMAIL;
import static com.example.newsfeed_project.exception.ErrorCode.NOT_FOUND_MEMBER;
import static com.example.newsfeed_project.exception.ErrorCode.SAME_PASSWORD;
import static com.example.newsfeed_project.exception.ErrorCode.WRONG_PASSWORD;

import com.example.newsfeed_project.config.PasswordEncoder;
import com.example.newsfeed_project.exception.DuplicatedException;
import com.example.newsfeed_project.exception.InvalidInputException;
import com.example.newsfeed_project.exception.NotFoundException;
import com.example.newsfeed_project.member.dto.MemberDto;
import com.example.newsfeed_project.member.entity.Member;
import com.example.newsfeed_project.member.repository.MemberRepository;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberServiceImpl(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public MemberDto createMember(MemberDto memberDto) {
        if (memberRepository.findByEmailIncludingDeleted(memberDto.getEmail()).isPresent()) {
            throw new DuplicatedException(EMAIL_EXIST); // 이미 존재하는 이메일 에러 처리
        }

        memberDto = encryptedPassword(memberDto);
        Member createMember = Member.toEntity(memberDto);
        Member save = memberRepository.save(createMember);
        return MemberDto.toDto(save);
    }

    @Override
    @Transactional
    public MemberDto updateMember(Long id, String password, MemberDto memberDto) {
        Member findMemberId = validateId(id);
        if (!passwordEncoder.matches(password, findMemberId.getPassword())) {
            throw new InvalidInputException(WRONG_PASSWORD);
        }

        findMemberId.updatedMember(memberDto);

        try {
            Member updatedMember = memberRepository.save(findMemberId);
            return MemberDto.toDto(updatedMember);
        } catch (OptimisticLockingFailureException e) {
            throw new RuntimeException("충돌이 발생했습니다. 다시 시도하세요");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public MemberDto getMemberById(Long id) {
        Member findMemberId = validateId(id);
        return MemberDto.toDto(findMemberId);
    }

    @Override
    @Transactional(readOnly = true)
    public MemberDto getMemberByEmail(String email) {
        Member member = validateEmail(email);

        return MemberDto.toDto(member);
    }

    @Override
    @Transactional
    public void deleteMemberById(Long id) {
        Member member = validateId(id);
        member.markAsDeleted(); // Soft 삭제 처리
        memberRepository.save(member); // 상태 저장
    }

    @Override
    @Transactional
    public void restoreMember(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_MEMBER));

        if (!member.isDeleted()) {
            throw new IllegalArgumentException("회원이 삭제되지 않았습니다.");
        }

        // 동일한 이메일을 사용하는 활성 회원이 있는지 검사
        if (memberRepository.findByEmail(member.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 동일한 이메일을 사용하는 회원이 존재합니다.");
        }

        member.restore();
        memberRepository.save(member);
    }

    @Override
    @Transactional
    public MemberDto changePassword(String oldPassword, String newPassword, HttpSession session) {
        Member member = validateEmail(session.getAttribute("email").toString());

        if (!passwordEncoder.matches(oldPassword, member.getPassword())) {
            throw new InvalidInputException(SAME_PASSWORD);
//            throw new IllegalArgumentException("Old password and new password do not match");
        }

        if (oldPassword.equals(newPassword)) {
            throw new IllegalArgumentException("비밀번호가 동일합니다.");
        }

        String encodedPassword = passwordEncoder.encode(newPassword);
        Member changePassword = member.withPassword(encodedPassword);
        changePassword = memberRepository.save(changePassword);

        // 비밀번호 변경 후 세션 무효화
        session.invalidate();

        return MemberDto.toDto(changePassword);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean authenticate(String email, String password) {
        Member member = validateEmail(email);

        if (member != null && passwordEncoder.matches(password, member.getPassword())) {
            return true;
        }
        return false;
    }

    public Member validateId(Long id) {
        return memberRepository.findById(id)
                .filter(member -> !member.isDeleted()) // 삭제된 회원 제외
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_MEMBER));
    }

    public Member validateEmail(String email) {
        return memberRepository.findByEmail(email)
                .filter(member -> !member.isDeleted()) // 삭제된 회원 제외
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_EMAIL));
    }

    private MemberDto encryptedPassword(MemberDto memberDto) {
        if (memberDto.getPassword() != null && !memberDto.getPassword().isEmpty()) {
            String encodedPassword = passwordEncoder.encode(memberDto.getPassword());
            memberDto = memberDto.withPassword(encodedPassword);
        }
        return memberDto;
    }
}