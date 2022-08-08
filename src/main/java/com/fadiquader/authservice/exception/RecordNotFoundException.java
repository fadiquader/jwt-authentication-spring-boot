package com.fadiquader.authservice.exception;

import com.fadiquader.authservice.exception.error.ErrorCodes;
import org.springframework.http.HttpStatus;

public class RecordNotFoundException extends ApiRequestException {

  public RecordNotFoundException(String message) {
    super(message, ErrorCodes.ERR_SYS_RESOURCE_NOT_FOUND.toString(), HttpStatus.NOT_FOUND);
  }

  public RecordNotFoundException(String message, String errorCode) {
    super(message, errorCode, HttpStatus.NOT_FOUND);
  }

  public RecordNotFoundException(String message,
                                 String errorCode,
                                 Throwable cause) {
    super(message, errorCode, HttpStatus.NOT_FOUND, cause);
  }
}
