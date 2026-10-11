package com.hospital.atenciones.infrastructure.persistence;

import org.springframework.stereotype.Component;

import com.hospital.atenciones.domain.entities.Atencion;
import com.hospital.atenciones.domain.repositories.AtencionRepository;
import com.hospital.atenciones.domain.valueobjects.AtencionId;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class AtencionRepositoryAdapter implements AtencionRepository {

    private final SpringDataAtencionRepository repository;

    public AtencionRepositoryAdapter(SpringDataAtencionRepository repository) {
        this.repository = repository;
    }

    @Override
    public Mono<Atencion> save(Atencion atencion) {
        AtencionR2dbcEntity entity = AtencionR2dbcEntity.fromDomain(atencion);
        return repository.save(entity).map(AtencionR2dbcEntity::toDomain);
    }

    @Override
    public Mono<Atencion> findById(AtencionId id) {
        return repository.findById(id.value()).map(AtencionR2dbcEntity::toDomain);
    }

    @Override
    public Mono<Boolean> existsByCitaId(Long citaId) {
        return repository.existsByCitaId(citaId);
    }

    @Override
    public Flux<Atencion> findByPacienteId(Long pacienteId) {
        return repository.findByPacienteId(pacienteId).map(AtencionR2dbcEntity::toDomain);
    }
}
