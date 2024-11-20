package com.example.newsfeed_project.newsfeed.service;

import com.example.newsfeed_project.member.entity.Member;
import com.example.newsfeed_project.member.repository.MemberRepository;
import com.example.newsfeed_project.member.service.MemberService;
import com.example.newsfeed_project.newsfeed.dto.LikeResonseDto;
import com.example.newsfeed_project.newsfeed.entity.Newsfeed;
import com.example.newsfeed_project.newsfeed.entity.NewsfeedLike;
import com.example.newsfeed_project.newsfeed.repository.NewsfeedLikeRepository;
import com.example.newsfeed_project.newsfeed.repository.NewsfeedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NewsfeedLikeServiceImpl implements NewsfeedLikeService {

  private final NewsfeedService newsfeedService;
  private final MemberService memberService;
  private final NewsfeedLikeRepository newsfeedLikeRepository;

  @Override
  public LikeResonseDto addLike(String email, long newsfeedId) {
    Member member = memberService.validateEmail(email);
    Newsfeed newsfeed = newsfeedService.findNewsfeedByIdOrElseThrow(newsfeedId);
    NewsfeedLike newsfeedLike = new NewsfeedLike();
    newsfeedLike.setMember(member);
    newsfeedLike.setNewsfeed(newsfeed);
    newsfeedLikeRepository.save(newsfeedLike);
    return new LikeResonseDto("좋아요 설정");
  }

  @Override
  public LikeResonseDto delLike(String email, long newsfeedId) {
    NewsfeedLike newsfeedLike = newsfeedLikeRepository.findByNewsfeedIdAndMember_Email(newsfeedId, email);
    newsfeedLikeRepository.delete(newsfeedLike);

    return new LikeResonseDto("좋아요 해제");
  }
}
