package com.hospital.atenciones.infrastructure.persistence;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface SpringDataAtencionRepository extends ReactiveCrudRepository<AtencionR2dbcEntity, Long> {
    Mono<Boolean> existsByCitaId(Long citaId);
    Flux<AtencionR2dbcEntity> findByPacienteId(Long pacienteId);
}
