package com.hospital.auditoria.domain.repositories;

import com.hospital.auditoria.domain.entities.AuditoriaLog;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AuditoriaRepository {
    Mono<AuditoriaLog> save(AuditoriaLog log);
    Flux<AuditoriaLog> findByRecursoAndRecursoId(String recurso, Long recursoId);
}
