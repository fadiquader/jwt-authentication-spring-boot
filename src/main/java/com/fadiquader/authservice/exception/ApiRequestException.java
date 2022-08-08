package com.fadiquader.authservice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class ApiRequestException extends RuntimeException {
  private String message;
  private String errorCode;
  private HttpStatus httpStatus;
  private Throwable cause;
//  private Map<String, String> validationErrors;

  public ApiRequestException(String message) {
    super(message);
  }
  public ApiRequestException(String message,
                             Throwable cause) {
    super(message, cause);
  }

  public ApiRequestException(String message,
                             String errorCode,
                             HttpStatus httpStatus) {
    this(message);
    this.message = message;
    this.errorCode = errorCode;
    this.httpStatus = httpStatus;
  }

  public ApiRequestException(String message,
                             String errorCode,
                             HttpStatus httpStatus,
                             Throwable cause
  ) {
    this(message, cause);
    this.message = message;
    this.errorCode = errorCode;
    this.httpStatus = httpStatus;
    this.cause = cause;
  }

  public String exceptionHandlingLogMessage() {
    return "Handling " + this.getClass().getSimpleName() + ": {}";
  }
}
