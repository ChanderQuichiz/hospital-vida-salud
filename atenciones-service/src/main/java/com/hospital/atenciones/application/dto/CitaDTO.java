package com.hospital.atenciones.application.dto;

public record CitaDTO(
    Long id,
    Long pacienteId,
    String estado,
    String especialidad
) {}
