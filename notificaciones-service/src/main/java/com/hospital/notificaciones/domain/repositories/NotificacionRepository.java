package com.hospital.notificaciones.domain.repositories;

import com.hospital.notificaciones.domain.entities.Notificacion;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface NotificacionRepository {
    Mono<Notificacion> save(Notificacion notificacion);
    Flux<Notificacion> findByPacienteId(Long pacienteId);
}
