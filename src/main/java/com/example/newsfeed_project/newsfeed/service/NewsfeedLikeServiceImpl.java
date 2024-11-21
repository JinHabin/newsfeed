package com.example.newsfeed_project.newsfeed.service;

import static com.example.newsfeed_project.exception.ErrorCode.ALREADY_LIKED;
import static com.example.newsfeed_project.exception.ErrorCode.NOT_FOUND_NEWSFEED_LIKE;
import com.example.newsfeed_project.exception.DuplicatedException;
import com.example.newsfeed_project.exception.NotFoundException;
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
    NewsfeedLike newsfeedLike = checkLike(email, newsfeedId);
    if(newsfeedLike.getId() > 0) {
      newsfeedLikeRepository.delete(newsfeedLike);
      return new LikeResponseDto("좋아요 해제");
    }else{
      newsfeedLikeRepository.save(newsfeedLike);
      return new LikeResponseDto("좋아요 설정");
    }
  }

  private NewsfeedLike checkLike(String email, long newsfeedId) {
    Member member = memberService.validateEmail(email);
    Newsfeed newsfeed = newsfeedService.findNewsfeedByIdOrElseThrow(newsfeedId);
    NewsfeedLike newsfeedLike = newsfeedLikeRepository.findByNewsfeedIdAndMemberId(newsfeedId, member.getId());
    if(newsfeedLike != null) {
      newsfeedLike.setMember(member);
      newsfeedLike.setNewsfeed(newsfeed);
    }else{
      newsfeedLike = new NewsfeedLike();
      newsfeedLike.setMember(member);
      newsfeedLike.setNewsfeed(newsfeed);
    }
    return newsfeedLike;
  }
}
