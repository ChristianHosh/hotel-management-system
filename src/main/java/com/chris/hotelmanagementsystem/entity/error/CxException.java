package com.chris.hotelmanagementsystem.entity.error;

import com.chris.hotelmanagementsystem.entity.CEntity;
import com.chris.hotelmanagementsystem.entity.OEntity;
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

  public static CxException forbidden(String message) {
    return new CxException(HttpStatus.FORBIDDEN, message);
  }

  public static CxException badRequest(CEntity entity, String message) {
    return new CxException(HttpStatus.BAD_REQUEST, String.format("(%s) %s", entity.getClass().getSimpleName(), message));
  }

  public static CxException unexpected(Throwable cause) {
    if (cause instanceof CxException cxException)
      return cxException;
    return new CxException("Internal System Error, please contact your admin", cause);
  }

  public static CxException hardcoded(String message) {
    return new CxException(message);
  }

  public static CxException hardcoded(HttpStatus httpStatus, String message) {
    return new CxException(httpStatus, message);
  }


  public static <T extends OEntity> Exception duplicate(Class<T> tClass, String field, Object value) {
    return new CxException(HttpStatus.CONFLICT, String.format("(%s) duplicate value for (%s): [%s]", tClass.getSimpleName(), field, value));
  }

  public static <T extends OEntity> CxException notFound(Class<T> tClass, String field, Object value) {
    return new CxException(HttpStatus.NOT_FOUND, String.format("(%s) with (%s) [%s] not found", tClass.getSimpleName(), field, value));
  }

  public static CxException unauthorized(String message) {
    return new CxException(HttpStatus.UNAUTHORIZED, message);
  }
}
