package com.hospital.pacientes.presentation;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.hospital.pacientes.domain.exceptions.PacienteDuplicadoException;
import com.hospital.pacientes.domain.exceptions.PacienteNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PacienteNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(PacienteNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
            "codigo", 404,
            "mensaje", ex.getMessage(),
            "fecha", LocalDateTime.now().toString()
        ));
    }

    @ExceptionHandler(PacienteDuplicadoException.class)
    public ResponseEntity<Map<String, Object>> handleConflict(PacienteDuplicadoException ex) {
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
}
