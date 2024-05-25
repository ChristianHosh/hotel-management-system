package com.chris.hotelmanagementsystem.entity.error;

import org.springframework.http.ResponseEntity;

public record ApiError(
    int status,
    String message,
    String details,
    long timestamp
) {

  public ApiError {
    if (status < 400)
      throw new IllegalArgumentException("status must be >= 400");
  }

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
