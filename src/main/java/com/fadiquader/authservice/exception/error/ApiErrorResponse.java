package com.fadiquader.authservice.exception.error;

import lombok.Data;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Data
public class ApiErrorResponse {
  private String errorCode;
  private String debugMessage;
  private ZonedDateTime timestamp;

  public ApiErrorResponse(String errorCode, String debugMessage) {
    this.errorCode = errorCode;
    this.debugMessage = debugMessage;
    this.timestamp = ZonedDateTime.now(ZoneId.of("Z"));
  }
}
