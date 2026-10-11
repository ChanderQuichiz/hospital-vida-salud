package com.hospital.auditoria.application.dto;

import java.time.LocalDateTime;

import com.hospital.auditoria.domain.entities.AuditoriaLog;

public record AuditoriaResponse(
    Long id,
    String eventoId,
    String tipoEvento,
    String recurso,
    Long recursoId,
    String estadoAnterior,
    String estadoNuevo,
    String origen,
    LocalDateTime fecha
) {
    public static AuditoriaResponse fromDomain(AuditoriaLog log) {
        return new AuditoriaResponse(
            log.getId(),
            log.getEventoId(),
            log.getTipoEvento(),
            log.getRecurso(),
            log.getRecursoId(),
            log.getEstadoAnterior(),
            log.getEstadoNuevo(),
            log.getOrigen(),
            log.getFecha()
        );
    }
}
