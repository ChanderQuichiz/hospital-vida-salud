package com.hospital.citas.application.dto;

import java.time.LocalDateTime;

import com.hospital.citas.domain.entities.Cita;

public record CitaResponse(
    Long id,
    String codigo,
    Long pacienteId,
    String especialidad,
    LocalDateTime fechaHora,
    String profesionalCodigo,
    String motivo,
    String estado,
    LocalDateTime fechaCreacion
) {
    public static CitaResponse fromDomain(Cita cita) {
        return new CitaResponse(
            cita.getId() != null ? cita.getId().value() : null,
            cita.getCodigo().value(),
            cita.getPacienteId(),
            cita.getEspecialidad(),
            cita.getFechaHora(),
            cita.getProfesionalCodigo(),
            cita.getMotivo(),
            cita.getEstado().name(),
            cita.getFechaCreacion()
        );
    }
}
