package com.chris.hotelmanagementsystem.entity.error;

import org.springframework.http.ResponseEntity;

public record ApiError(
    int status,
    String message,
    String details,
    long timestamp
) {

  public static ApiError from(CxException e) {
    return new ApiError(
        e.getHttpStatus().value(),
        e.getHttpStatus().getReasonPhrase(),
        e.getMessage(),
        System.currentTimeMillis()
    );
  }

  public ResponseEntity<ApiError> toResponse() {
    return ResponseEntity.status(status).body(this);
  }
}
