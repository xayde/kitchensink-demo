package org.jboss.eap.quickstarts.kitchensink.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.jboss.eap.quickstarts.kitchensink.controller.dto.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class RestExceptionHandler {
  @ExceptionHandler(MemberNotFoundException.class)
  protected ResponseEntity<ErrorDTO> handleException(MemberNotFoundException e) {
    return handleExceptionAndReturn(e, HttpStatus.NOT_FOUND, e.getMessage());
  }

  @ExceptionHandler(MemberAlreadyExistsException.class)
  protected ResponseEntity<ErrorDTO> handleException(MemberAlreadyExistsException e) {
    return handleExceptionAndReturn(e, HttpStatus.CONFLICT, e.getMessage());
  }

  @ExceptionHandler(ConstraintViolationException.class)
  protected ResponseEntity<ErrorDTO> handleException(ConstraintViolationException e) {
    var violations = e.getConstraintViolations();
    log.info("Validation completed. violations found: " + violations.size());
    Map<String, String> response = new HashMap<>();
    for (ConstraintViolation<?> violation : violations) {
      response.put(violation.getPropertyPath().toString(), violation.getMessage());
    }
    return handleExceptionAndReturn(e, HttpStatus.BAD_REQUEST, response.toString());
  }

  @ExceptionHandler(Exception.class)
  protected ResponseEntity<ErrorDTO> handleException(Exception e) {
    return handleExceptionAndReturn(e, HttpStatus.BAD_REQUEST, e.getMessage());
  }

  private ResponseEntity<ErrorDTO> handleExceptionAndReturn(
      Exception e, HttpStatus httpStatus, String message) {
    handle(e);
    return ResponseEntity.status(httpStatus).body(ErrorDTO.builder().message(message).build());
  }

  private void handle(Exception e) {
    log(e);
  }

  private void log(Exception e) {
    log.error(e.getMessage(), e);
  }
}
