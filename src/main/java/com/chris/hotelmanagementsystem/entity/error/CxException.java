package com.chris.hotelmanagementsystem.entity.error;

import com.chris.hotelmanagementsystem.entity.CEntity;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CxException extends RuntimeException {

  private final HttpStatus httpStatus;

  private CxException(HttpStatus httpStatus, String message) {
    super(message);
    this.httpStatus = httpStatus;
  }

  private CxException(String message, Throwable cause) {
    super(message, cause);
    this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
  }

  private CxException(String message) {
    super(message);
    this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
  }

  public static <T extends CEntity> CxException notFound(Class<T> tClass, Long id) {
    return new CxException(HttpStatus.NOT_FOUND, String.format("(%s) with id [%d] not found", tClass.getSimpleName(), id));
  }

  public static CxException forbidden(String message) {
    return new CxException(HttpStatus.FORBIDDEN, message);
  }

  public static CxException badRequest(String message) {
    return new CxException(HttpStatus.BAD_REQUEST, message);
  }

  public static CxException unexpected(Throwable cause) {
    if (cause instanceof CxException cxException)
      return cxException;
    return new CxException("Internal Server Error", cause);
  }
  
  public static CxException hardcoded(String message) {
    return new CxException(message);
  }


  public static <T extends CEntity> Exception duplicate(Class<T> tClass, String field, Object value) {
    return new CxException(HttpStatus.CONFLICT, String.format("(%s) duplicate value for (%s): [%s]", tClass.getSimpleName(), field, value));
  }
}
