package com.fadiquader.authservice.exception;

import com.fadiquader.authservice.exception.error.ApiErrorResponse;
import com.fadiquader.authservice.logging.JasonEncoder;
import com.fadiquader.authservice.logging.LoggingKeys;
import com.fadiquader.authservice.logging.RestLoggingKeys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
  Logger log = LoggerFactory.getLogger(ApiExceptionHandler.class);

  @ExceptionHandler(ApiRequestException.class)
  public ResponseEntity<ApiErrorResponse> handleApiException(ApiRequestException ex) {
    if (ex.getHttpStatus().is5xxServerError()) {
      log.error(
          ex.exceptionHandlingLogMessage(),
          JasonEncoder.kv(LoggingKeys.MDC_KEY_LOG_EVENT, RestLoggingKeys.LOG_EVENT_5XX_EX),
          ex
      );
    } else  {
      log.warn(
          ex.exceptionHandlingLogMessage(),
          JasonEncoder.kv(LoggingKeys.MDC_KEY_LOG_EVENT, RestLoggingKeys.LOG_EVENT_EX),
          ex
      );
    }

    return ResponseEntity
        .status(ex.getHttpStatus())
        .body(new ApiErrorResponse(ex.getErrorCode(), ex.getMessage()));
  }

  @ExceptionHandler(UsernameNotFoundException.class)
  public ResponseEntity<ApiErrorResponse> handleUsernameNotFoundException(UsernameNotFoundException ex) {
    log.warn(
            "Handling UsernameNotFoundException: {}",
            JasonEncoder.kv(LoggingKeys.MDC_KEY_LOG_EVENT, RestLoggingKeys.LOG_EVENT_EX),
            ex
    );

    return ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body(new ApiErrorResponse(HttpStatus.NOT_FOUND.name(), ex.getMessage()));
  }
}
