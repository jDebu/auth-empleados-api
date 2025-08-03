package com.jose.authempleadosapi.exception;

import java.time.LocalDateTime;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {

  @ExceptionHandler({UsernameNotFoundException.class, BadCredentialsException.class})
  public ResponseEntity<?> handleAuthExceptions(RuntimeException ex) {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
        .body(
            Map.of(
                "timestamp",
                LocalDateTime.now(),
                "status",
                401,
                "error",
                "Unauthorized",
                "message",
                ex.getMessage(),
                "path",
                "/auth/login"));
  }
}
