package com.chris.hotelmanagementsystem.entity.error;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CxExceptionHandler {

  @ExceptionHandler(CxException.class)
  public ApiError handle(CxException cxException) {
    return ApiError.from(cxException);
  }

  @ExceptionHandler(Exception.class)
  public ApiError handle(Exception exception) {
    return ApiError.from(CxException.unexpected(exception));
  }
}
