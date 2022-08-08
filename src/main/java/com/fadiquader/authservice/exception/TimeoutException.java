package com.fadiquader.authservice.exception;

import com.fadiquader.authservice.exception.error.ErrorCodes;
import org.springframework.http.HttpStatus;

public class TimeoutException extends ApiRequestException {

  public TimeoutException(String message, Throwable cause) {
    super(message, ErrorCodes.ERR_SYS_DEPENDENCIES_TIMEOUT_ERROR.toString(), HttpStatus.SERVICE_UNAVAILABLE, cause);
  }
  public TimeoutException(String message, String errorCode, Throwable cause) {
    super(message, errorCode, HttpStatus.SERVICE_UNAVAILABLE, cause);
  }
}
