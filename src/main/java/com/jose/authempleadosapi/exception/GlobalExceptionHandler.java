package com.jose.authempleadosapi.exception;

import java.util.HashMap;
import java.util.Map;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<?> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
    if (ex.getCause() instanceof ConstraintViolationException) {
      return ResponseEntity.status(HttpStatus.CONFLICT)
          .body(Map.of("username", "El nombre de usuario ya está registrado"));
    }
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(Map.of("error", "Error de integridad de datos"));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<?> handleValidationErrors(MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult()
        .getFieldErrors()
        .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
    return ResponseEntity.badRequest().body(errors);
  }

  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<?> handleRuntime(RuntimeException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", ex.getMessage()));
  }
}
