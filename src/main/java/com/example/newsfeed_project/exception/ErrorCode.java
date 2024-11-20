package com.example.newsfeed_project.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
  //비밀번호가 틀렸을 때 출력하는 오류 메시지
  WRONG_PASSWORD("비밀 번호가 틀렸습니다.", HttpStatus.BAD_REQUEST),
  //비밀번호 변경 시 이전 비밀번호와 같은 비밀번호를 사용했을 때 출력하는 오류 메시지
  SAME_PASSWORD("비밀번호는 다른 비밀번호를 사용하여야합니다.", HttpStatus.BAD_REQUEST),
  //탈퇴한 이메일로 가입을 시도할 때 출력하는 오류 메시지
  EMAIL_DELETED("삭제된 이메일 입니다.", HttpStatus.BAD_REQUEST),

  //중복된 이메일로 가일 할 때 출력하는 오류 메시지
  EMAIL_EXIST("중복된 아이디 입니다.", HttpStatus.BAD_REQUEST),
  //이메일의 형식이 틀렸을 때 출력하는 오류 메시지
  EMAIL_VALIDATION("이메일의 형식이 틀렸습니다.", HttpStatus.BAD_REQUEST),
  //비밀번호의 형식이 틀렸을 때 출력하는 오류 메시지
  PASSWORD_VALIDATION("비밀번호의 형식이 틀렸습니다.", HttpStatus.BAD_REQUEST),

  //권한이 없는 사용자가 수정, 삭제를 하려고 할 때
  NO_AUTHOR("수정, 삭제는 작성자만 할 수 있습니다.", HttpStatus.UNAUTHORIZED),;

  private final String message;
  private final HttpStatus httpStatus;

  ErrorCode(String message, HttpStatus httpStatus) {
    this.message = message;
    this.httpStatus = httpStatus;
  }
}
