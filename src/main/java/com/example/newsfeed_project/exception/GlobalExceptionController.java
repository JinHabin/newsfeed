package com.example.newsfeed_project.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionController {

  @ExceptionHandler
  public ResponseEntity<String> incorrectException(IncorrectException e) {
    return new ResponseEntity<>(e.getErrorCode().getMessage(), e.getErrorCode().getHttpStatus());
  }

  @ExceptionHandler
  public ResponseEntity<String> validationException(ValidationException e) {
    return new ResponseEntity<>(e.getErrorCode().getMessage(), e.getErrorCode().getHttpStatus());
  }

  @ExceptionHandler
  public ResponseEntity<String> authorException(AuthorException e) {
    return new ResponseEntity<>(e.getErrorCode().getMessage(), e.getErrorCode().getHttpStatus());
  }

  @ExceptionHandler
  public ResponseEntity<String> constrainViolationException(ConstraintViolationException e) {
    return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
  }

//  @ExceptionHandler(Exception.class)
//  public ResponseEntity<String> constraintViolationException(Exception e) {
//    return new ResponseEntity<>("errorrrororrorororororororro", HttpStatus.BAD_REQUEST);
//  }

}
