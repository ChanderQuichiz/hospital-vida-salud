package com.hospital.citas.application.dto;

import java.time.LocalDateTime;

public record CitaRequest(
    Long pacienteId,
    String especialidad,
    LocalDateTime fechaHora,
    String profesionalCodigo,
    String motivo
) {}
