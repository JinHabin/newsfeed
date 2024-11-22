package com.example.newsfeed_project.comment.service;

import com.example.newsfeed_project.comment.dto.CommentDto;
import com.example.newsfeed_project.comment.entity.Comment;
import com.example.newsfeed_project.comment.repository.CommentRepository;
import com.example.newsfeed_project.member.entity.Member;
import com.example.newsfeed_project.member.service.MemberService;
import com.example.newsfeed_project.newsfeed.entity.Newsfeed;
import com.example.newsfeed_project.newsfeed.service.NewsfeedService;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final MemberService memberService;
    private final NewsfeedService newsfeedService;

    public CommentService(CommentRepository commentRepository, MemberService memberService, NewsfeedService newsfeedService) {
        this.commentRepository = commentRepository;
        this.memberService = memberService;
        this.newsfeedService = newsfeedService;
    }

    //댓글 생성
    public CommentDto createComment(Long newsfeedId, CommentDto dto, HttpSession session) {
        Newsfeed newsfeed = newsfeedService.findNewsfeedByIdOrElseThrow(newsfeedId);
        String email = (String) session.getAttribute("email");
        Member member = memberService.validateEmail(email);
        Comment comment = Comment.toEntity(dto);
        comment.setMember(member);
        comment.setNewsFeed(newsfeed);
        Comment save = commentRepository.save(comment);

        return CommentDto.toDto(save);
    }

    //댓글 전체 조회
    public List<CommentDto> findAll(Long newsfeedId, Pageable pageable) {
        return commentRepository.findByFeedId(newsfeedId,pageable).stream().map(CommentDto::toDto).toList();
    }

    //댓글 단건 조회
    public CommentDto findById(Long id) {
        Comment findId = commentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("댓글을 찾지 못했습니다."));

        return new CommentDto(findId.getContents(), findId.getCreatedAt());
    }

    //댓글 수정
    public CommentDto updateComment(Long id, CommentDto dto) {
        Comment findId = commentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("댓글을 찾지 못했습니다."));

        findId.updateComment(dto);
        Comment save = commentRepository.save(findId);

        return CommentDto.toDto(save);
    }

    //댓글 삭제
    public void deleteComment(Long id) {
        commentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("댓글을 찾지 못했습니다."));
        commentRepository.deleteById(id);
    }

    public Comment findCommentByIdOrElseThrow(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("댓글 찾지 못했습니다."));
    }
}
