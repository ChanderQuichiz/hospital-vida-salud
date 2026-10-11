package com.hospital.citas.application.dto;

public record PacienteDTO(
    Long id,
    String tipoDocumento,
    String numeroDocumento,
    String nombres,
    String apellidos,
    String estado
) {}
