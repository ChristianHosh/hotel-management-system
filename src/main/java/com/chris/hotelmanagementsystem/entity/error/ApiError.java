package com.chris.hotelmanagementsystem.entity.error;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record ApiError(
    int status,
    String message,
    String details,
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime timestamp
) {

  public static ApiError from(CxException e) {
    return new ApiError(
        e.getHttpStatus().value(),
        e.getHttpStatus().getReasonPhrase(),
        e.getMessage(),
        LocalDateTime.now()
    );
  }
}
