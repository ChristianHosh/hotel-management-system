package com.chris.hotelmanagementsystem.entity.error;

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
}
