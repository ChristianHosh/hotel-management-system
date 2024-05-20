package com.chris.hotelmanagementsystem.entity.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@ControllerAdvice
public class CxExceptionHandler {

  @ResponseBody
  @ExceptionHandler(CxException.class)
  public ResponseEntity<ApiError> handle(CxException cxException) {
    if (cxException.getHttpStatus() == HttpStatus.INTERNAL_SERVER_ERROR)
      log.error("server error: {}", cxException.getMessage(), cxException);
    else
      log.warn("user error: {}", cxException.getMessage(), cxException);
    return ResponseEntity
        .status(cxException.getHttpStatus())
        .body(ApiError.from(cxException));
  }

  @ResponseBody
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiError> handle(Exception exception) {
    return handle(CxException.unexpected(exception));
  }
}
