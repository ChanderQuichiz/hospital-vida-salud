package com.hospital.citas.presentation;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.hospital.citas.domain.exceptions.CitaNotFoundException;
import com.hospital.citas.domain.exceptions.ConflictException;
import com.hospital.citas.domain.exceptions.ServiceUnavailableException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CitaNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(CitaNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
            "codigo", 404,
            "mensaje", ex.getMessage(),
            "fecha", LocalDateTime.now().toString()
        ));
    }

    @ExceptionHandler({ConflictException.class, IllegalStateException.class})
    public ResponseEntity<Map<String, Object>> handleConflict(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
            "codigo", 409,
            "mensaje", ex.getMessage(),
            "fecha", LocalDateTime.now().toString()
        ));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleBadRequest(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
            "codigo", 400,
            "mensaje", ex.getMessage(),
            "fecha", LocalDateTime.now().toString()
        ));
    }

    @ExceptionHandler(ServiceUnavailableException.class)
    public ResponseEntity<Map<String, Object>> handleServiceUnavailable(ServiceUnavailableException ex) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(Map.of(
            "codigo", 503,
            "mensaje", ex.getMessage(),
            "fecha", LocalDateTime.now().toString()
        ));
    }
}
