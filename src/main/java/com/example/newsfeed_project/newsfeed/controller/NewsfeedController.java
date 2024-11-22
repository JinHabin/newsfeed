package com.example.newsfeed_project.newsfeed.controller;

import com.example.newsfeed_project.newsfeed.dto.NewsfeedRequestDto;
import com.example.newsfeed_project.newsfeed.dto.NewsfeedResponseDto;
import com.example.newsfeed_project.newsfeed.dto.NewsfeedTermRequestDto;
import com.example.newsfeed_project.newsfeed.service.NewsfeedService;
import com.example.newsfeed_project.util.SessionUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 뉴스피드컨트롤러
 */
@RestController
@RequestMapping("/newsfeeds")
@RequiredArgsConstructor
public class NewsfeedController {

  private final NewsfeedService newsfeedService;

  //뉴스피드를 저장하는 메서드
  @PostMapping
  public ResponseEntity<NewsfeedResponseDto> save(
      @Valid @RequestBody NewsfeedRequestDto newsfeedRequestDto,
      HttpServletRequest request
  ) {
    HttpSession session = request.getSession(false);
    String email = SessionUtil.validateSession(session);
    NewsfeedResponseDto newsfeedResponseDto = newsfeedService.save(newsfeedRequestDto, email);
    return new ResponseEntity<>(newsfeedResponseDto, HttpStatus.CREATED);
  }

  //전체 조회를 하는 메서드 / 좋아요순 정렬기능이 있음
  @GetMapping
  public ResponseEntity<List<NewsfeedResponseDto>> findAll(
      @RequestParam(required = false, defaultValue = "false") boolean isLike,
      @PageableDefault(size = 10, sort = "updatedAt", direction = Direction.DESC)
      Pageable pageable
  ){
    List<NewsfeedResponseDto> list = newsfeedService.findAll(isLike, pageable);
    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  //사용자를 기준으로 조회를 하는 메서드 / 좋아요순 정렬기능이 있음
  @GetMapping("/member/{memberId}")
  public ResponseEntity<List<NewsfeedResponseDto>> findByMemberId(
      @PathVariable long memberId,
      @RequestParam(required = false, defaultValue = "false") boolean isLike,
      @PageableDefault(size = 10, sort = "createdAt", direction = Direction.DESC)
      Pageable pageable
  ){
    List<NewsfeedResponseDto> list = newsfeedService.findByMemberId(memberId, isLike, pageable);
    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  //기간검색 / 좋아요순 정렬기능이 있음
  @GetMapping("/term")
  public ResponseEntity<List<NewsfeedResponseDto>> findAllByTerm(
      @Valid @RequestBody NewsfeedTermRequestDto newsfeedTermRequestDto,
      @RequestParam(required = false, defaultValue = "false") boolean isLike,
      @PageableDefault(size = 10, sort = "updatedAt", direction = Direction.DESC)
      Pageable pageable
  ){
    List<NewsfeedResponseDto> list = newsfeedService.findAllByTerm(newsfeedTermRequestDto, isLike, pageable);
    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  //뉴스피드를 업데이트하는 메서드
  @PatchMapping("/{id}")
  public ResponseEntity<NewsfeedResponseDto> updateNewsfeed(
      @PathVariable Long id,
      @Valid @RequestBody NewsfeedRequestDto newsfeedRequestDto,
      HttpServletRequest request
  ){
    HttpSession session = request.getSession(false);
    String email = SessionUtil.validateSession(session);
    NewsfeedResponseDto newsfeedResponseDto = newsfeedService.updateNewsfeed(id, newsfeedRequestDto, email);
    return new ResponseEntity<>(newsfeedResponseDto, HttpStatus.OK);
  }

  //뉴스피드를 삭제하는 메서드
  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteNewsfeed(
      @PathVariable Long id,
      HttpServletRequest request
  ){
    HttpSession session = request.getSession(false);
    String email = SessionUtil.validateSession(session);
    newsfeedService.delete(id, email);
    return new ResponseEntity<>("Deleted", HttpStatus.OK);
  }
}
