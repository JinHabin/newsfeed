package com.example.newsfeed_project.newsfeed.service;

import com.example.newsfeed_project.member.entity.Member;
import com.example.newsfeed_project.member.service.MemberService;
import com.example.newsfeed_project.newsfeed.dto.LikeResponseDto;
import com.example.newsfeed_project.newsfeed.entity.Newsfeed;
import com.example.newsfeed_project.newsfeed.entity.NewsfeedLike;
import com.example.newsfeed_project.newsfeed.repository.NewsfeedLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NewsfeedLikeServiceImpl implements NewsfeedLikeService {

  private final NewsfeedService newsfeedService;
  private final MemberService memberService;
  private final NewsfeedLikeRepository newsfeedLikeRepository;

  @Override
  public LikeResponseDto addLike(String email, long newsfeedId) {
    Member member = memberService.validateEmail(email);
    Newsfeed newsfeed = newsfeedService.findNewsfeedByIdOrElseThrow(newsfeedId);
    NewsfeedLike newsfeedLike = new NewsfeedLike();
    newsfeedLike.setMember(member);
    newsfeedLike.setNewsfeed(newsfeed);
    checkNewsfeedLikeExciest(newsfeedId, member.getId());
    newsfeedLikeRepository.save(newsfeedLike);
    return new LikeResponseDto("좋아요 설정");
  }

  @Override
  public LikeResponseDto delLike(String email, long newsfeedId) {
    long memberId = memberService.validateEmail(email).getId();
    NewsfeedLike newsfeedLike = newsfeedLikeRepository.findByNewsfeedIdAndMemberId(newsfeedId, memberId);
    newsfeedLikeRepository.delete(newsfeedLike);

    return new LikeResponseDto("좋아요 해제");
  }

  private void checkNewsfeedLikeExciest(long newsfeedId, long memberId) {
    NewsfeedLike newsfeedLike = newsfeedLikeRepository.findByNewsfeedIdAndMemberId(newsfeedId, memberId);
    if (newsfeedLike != null) {
      throw new RuntimeException("존재합니다.");
    }
  }
}
