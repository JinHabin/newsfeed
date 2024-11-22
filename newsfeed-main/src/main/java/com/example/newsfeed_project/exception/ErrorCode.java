package com.example.newsfeed_project.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

  //InvalidInputException
  //비밀번호 변경 시 이전 비밀번호와 같은 비밀번호를 사용했을 때 출력하는 오류 메시지
  SAME_PASSWORD("비밀번호는 다른 비밀번호를 사용하여야합니다.", HttpStatus.BAD_REQUEST),
  //비밀번호가 틀렸을 때 출력하는 오류 메시지
  WRONG_PASSWORD("비밀 번호가 틀렸습니다.", HttpStatus.BAD_REQUEST),
  //아이디 비밀번호가 잘못됨
  DIFFERENT_EMAIL_PASSWORD("이메일 혹은 비밀번호가 잘못되었습니다.", HttpStatus.BAD_REQUEST),
  //탈퇴한 이메일로 가입을 시도할 때 출력하는 오류 메시지
  EMAIL_DELETED("삭제된 이메일 입니다.", HttpStatus.BAD_REQUEST),

  // NotFoundException

  NOT_FOUND_NEWSFEED("뉴스피드를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),

  NOT_FOUND_NEWSFEED_LIKE("좋아요를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),

  NOT_FOUND_MEMBER("멤버를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),

  NOT_FOUND_EMAIL("이메일을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),

  NOT_FOUND_COMMENT("댓글을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),

  // InternalServerException
  //세션이 만료되었을 때 출력하는 오류 메시지
  SESSION_TIMEOUT("세션이 만료되었습니다.", HttpStatus.SERVICE_UNAVAILABLE),

  // DuplicatedException
  //이미 좋아요를 누른 게시글에 좋아요를 누른 경우
  ALREADY_LIKED("이미 좋아요를 누른 게시물입니다.", HttpStatus.BAD_REQUEST),
  //중복된 이메일로 가일 할 때 출력하는 오류 메시지
  EMAIL_EXIST("중복된 아이디 입니다.", HttpStatus.BAD_REQUEST),

  // NoAuthorizedException
  //로그인이 안 되었을 떄 출력하는 오류 메시지
  NO_SESSION("로그인이 필요합니다.", HttpStatus.UNAUTHORIZED),
  //권한이 없는 사용자가 수정, 삭제를 하려고 할 때
  NO_AUTHOR_CHANGE("수정, 삭제는 작성자만 할 수 있습니다.", HttpStatus.UNAUTHORIZED),
  //프로필 본인 조회
  NO_AUTHOR_PROFILE("프로필은 본인만 조회할 수 있습니다.", HttpStatus.UNAUTHORIZED),

  NO_SELF_LIKE("자신이 작성한 게시글과 댓글에 좋아요를 달 수 없습니다.", HttpStatus.UNAUTHORIZED),



  //로그인
  //로그인 실패
  LOGIN_FAIL("로그인에 실패했습니다.", HttpStatus.BAD_REQUEST),
  //비밀번호의 형식이 틀렸을 때 출력하는 오류 메시지
//  PASSWORD_VALIDATION("비밀번호의 형식이 틀렸습니다.", HttpStatus.BAD_REQUEST),
  //충돌오류? MemberServiceImpl #60
  CONFILX("충돌이 발생했습니다.", HttpStatus.BAD_REQUEST);

  private final String message;
  private final HttpStatus httpStatus;

  ErrorCode(String message, HttpStatus httpStatus) {
    this.message = message;
    this.httpStatus = httpStatus;
  }
}
