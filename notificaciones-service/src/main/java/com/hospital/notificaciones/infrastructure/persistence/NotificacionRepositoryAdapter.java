package com.hospital.notificaciones.infrastructure.persistence;

import org.springframework.stereotype.Component;

import com.hospital.notificaciones.domain.entities.Notificacion;
import com.hospital.notificaciones.domain.repositories.NotificacionRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class NotificacionRepositoryAdapter implements NotificacionRepository {

    private final SpringDataNotificacionRepository repository;

    public NotificacionRepositoryAdapter(SpringDataNotificacionRepository repository) {
        this.repository = repository;
    }

    @Override
    public Mono<Notificacion> save(Notificacion notificacion) {
        NotificacionR2dbcEntity entity = NotificacionR2dbcEntity.fromDomain(notificacion);
        return repository.save(entity).map(NotificacionR2dbcEntity::toDomain);
    }

    @Override
    public Flux<Notificacion> findByPacienteId(Long pacienteId) {
        return repository.findByPacienteId(pacienteId).map(NotificacionR2dbcEntity::toDomain);
    }
}
