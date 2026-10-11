package com.hospital.notificaciones.application.usecases;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.hospital.notificaciones.domain.entities.Notificacion;
import com.hospital.notificaciones.domain.repositories.NotificacionRepository;

import reactor.core.publisher.Mono;

@Service
public class ProcesarNotificacionEventoUseCase {

    private static final Logger log = LoggerFactory.getLogger(ProcesarNotificacionEventoUseCase.class);

    private final NotificacionRepository notificacionRepository;

    public ProcesarNotificacionEventoUseCase(NotificacionRepository notificacionRepository) {
        this.notificacionRepository = notificacionRepository;
    }

    public Mono<Void> execute(Map<String, Object> evento) {
        try {
            String eventoId = evento.get("eventoId").toString();
            String tipo = evento.get("tipo").toString();
            Map<String, Object> datos = (Map<String, Object>) evento.get("datos");

            Long pacienteId = Long.valueOf(datos.get("pacienteId").toString());
            Long citaId = Long.valueOf(datos.get("citaId").toString());
            String especialidad = datos.get("especialidad") != null ? datos.get("especialidad").toString() : "";
            String fechaHoraCita = datos.get("fechaHoraCita") != null ? datos.get("fechaHoraCita").toString() : "";

            String mensaje = String.format("Aviso de Cita [%s]: Su cita de %s programada para %s ha cambiado de estado.", tipo, especialidad, fechaHoraCita);

            Notificacion notificacion = Notificacion.create(eventoId, pacienteId, citaId, tipo, mensaje);

            return notificacionRepository.save(notificacion)
                    .doOnSuccess(saved -> log.info("Notificacion registrada exitosamente para eventoId: {}", eventoId))
                    .onErrorResume(ex -> {
                        log.warn("Evento duplicado o ya procesado en Notificaciones (eventoId: {})", eventoId);
                        return Mono.empty();
                    })
                    .then();
        } catch (Exception e) {
            log.error("Error procesando notificacion: {}", e.getMessage());
            return Mono.empty();
        }
    }
}
