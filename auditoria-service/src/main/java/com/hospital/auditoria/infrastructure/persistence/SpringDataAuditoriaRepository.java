package com.hospital.auditoria.infrastructure.persistence;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Flux;

@Repository
public interface SpringDataAuditoriaRepository extends ReactiveCrudRepository<AuditoriaR2dbcEntity, Long> {
    Flux<AuditoriaR2dbcEntity> findByRecursoAndRecursoId(String recurso, Long recursoId);
}
