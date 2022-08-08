package com.fadiquader.authservice.exception;

import org.springframework.http.HttpStatus;

public class ValidationException extends ApiRequestException {

  public ValidationException(String message,
                             String errorCode,
                             Throwable cause) {
    super(message, errorCode, HttpStatus.UNPROCESSABLE_ENTITY, cause);
  }
}
