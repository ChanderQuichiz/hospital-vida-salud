package com.hospital.pacientes.infrastructure.persistence;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Mono;

@Repository
public interface SpringDataPacienteRepository extends ReactiveCrudRepository<PacienteR2dbcEntity, Long> {
    Mono<Boolean> existsByTipoDocumentoAndNumeroDocumento(String tipoDocumento, String numeroDocumento);
}
