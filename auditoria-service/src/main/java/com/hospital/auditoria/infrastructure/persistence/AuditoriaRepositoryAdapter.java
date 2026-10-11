package com.hospital.auditoria.infrastructure.persistence;

import org.springframework.stereotype.Component;

import com.hospital.auditoria.domain.entities.AuditoriaLog;
import com.hospital.auditoria.domain.repositories.AuditoriaRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class AuditoriaRepositoryAdapter implements AuditoriaRepository {

    private final SpringDataAuditoriaRepository repository;

    public AuditoriaRepositoryAdapter(SpringDataAuditoriaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Mono<AuditoriaLog> save(AuditoriaLog log) {
        AuditoriaR2dbcEntity entity = AuditoriaR2dbcEntity.fromDomain(log);
        return repository.save(entity).map(AuditoriaR2dbcEntity::toDomain);
    }

    @Override
    public Flux<AuditoriaLog> findByRecursoAndRecursoId(String recurso, Long recursoId) {
        return repository.findByRecursoAndRecursoId(recurso, recursoId).map(AuditoriaR2dbcEntity::toDomain);
    }
}
