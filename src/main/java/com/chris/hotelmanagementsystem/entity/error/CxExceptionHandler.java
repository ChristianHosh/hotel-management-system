package com.chris.hotelmanagementsystem.entity.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@Slf4j
@ControllerAdvice
public class CxExceptionHandler {

  @ExceptionHandler(CxException.class)
  public ResponseEntity<ApiError> handle(CxException cxException) {
    if (cxException.getHttpStatus().is5xxServerError())
      log.error("server error: {}", cxException.getMessage(), cxException);
    else
      log.warn("user error: {}", cxException.getMessage(), cxException);
    return ApiError.from(cxException).toResponse();
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiError> handle(Exception exception) {
    return handle(CxException.unexpected(exception));
  }

  @ExceptionHandler(NoHandlerFoundException.class)
  public ResponseEntity<ApiError> handle(NoHandlerFoundException noHandlerFoundException) {
    return handle(CxException.hardcoded(HttpStatus.NOT_FOUND, noHandlerFoundException.getMessage()));
  }
}
