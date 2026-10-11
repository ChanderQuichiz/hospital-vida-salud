package com.hospital.notificaciones.infrastructure.persistence;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Flux;

@Repository
public interface SpringDataNotificacionRepository extends ReactiveCrudRepository<NotificacionR2dbcEntity, Long> {
    Flux<NotificacionR2dbcEntity> findByPacienteId(Long pacienteId);
}
