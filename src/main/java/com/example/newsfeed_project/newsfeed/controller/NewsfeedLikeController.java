package com.example.newsfeed_project.newsfeed.controller;

import com.example.newsfeed_project.newsfeed.dto.LikeResonseDto;
import com.example.newsfeed_project.newsfeed.service.NewsfeedLikeService;
import com.example.newsfeed_project.util.SessionUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/newsfeed/like")
@RequiredArgsConstructor
public class NewsfeedLikeController {

  private final NewsfeedLikeService newsfeedLikeService;

  @PostMapping("/{newsfeedId}")
  public ResponseEntity<LikeResonseDto> addLike(
      @PathVariable long newsfeedId,
      HttpServletRequest request
  ){
    String email = SessionUtil.validateSession(request.getSession());
    LikeResonseDto likeResonseDto = newsfeedLikeService.addLike(email, newsfeedId);
    return new ResponseEntity<>(likeResonseDto, HttpStatus.OK);
  }

  @DeleteMapping("/{newsfeedId}")
  public ResponseEntity<LikeResonseDto> delLike(
      @PathVariable long newsfeedId,
      HttpServletRequest request
  ){
    String email = SessionUtil.validateSession(request.getSession());
    LikeResonseDto likeResonseDto = newsfeedLikeService.delLike(email, newsfeedId);
    return new ResponseEntity<>(likeResonseDto, HttpStatus.OK);
  }

}
