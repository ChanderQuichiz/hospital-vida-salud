package com.hospital.notificaciones.application.dto;

import java.time.LocalDateTime;

import com.hospital.notificaciones.domain.entities.Notificacion;

public record NotificacionResponse(
    Long id,
    String eventoId,
    Long pacienteId,
    Long citaId,
    String tipo,
    String mensaje,
    LocalDateTime fechaEnvio
) {
    public static NotificacionResponse fromDomain(Notificacion notificacion) {
        return new NotificacionResponse(
            notificacion.getId(),
            notificacion.getEventoId(),
            notificacion.getPacienteId(),
            notificacion.getCitaId(),
            notificacion.getTipo(),
            notificacion.getMensaje(),
            notificacion.getFechaEnvio()
        );
    }
}
