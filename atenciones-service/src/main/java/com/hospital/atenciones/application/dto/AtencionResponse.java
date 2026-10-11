package com.hospital.atenciones.application.dto;

import java.time.LocalDateTime;

import com.hospital.atenciones.domain.entities.Atencion;

public record AtencionResponse(
    Long id,
    String codigo,
    Long pacienteId,
    Long citaId,
    LocalDateTime fechaAtencion,
    String area,
    String estado
) {
    public static AtencionResponse fromDomain(Atencion atencion) {
        return new AtencionResponse(
            atencion.getId() != null ? atencion.getId().value() : null,
            atencion.getCodigo().value(),
            atencion.getPacienteId(),
            atencion.getCitaId(),
            atencion.getFechaAtencion(),
            atencion.getArea(),
            atencion.getEstado().name()
        );
    }
}
