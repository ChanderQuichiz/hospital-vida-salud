package com.hospital.atenciones.domain.repositories;

import com.hospital.atenciones.domain.entities.Atencion;
import com.hospital.atenciones.domain.valueobjects.AtencionId;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AtencionRepository {
    Mono<Atencion> save(Atencion atencion);
    Mono<Atencion> findById(AtencionId id);
    Mono<Boolean> existsByCitaId(Long citaId);
    Flux<Atencion> findByPacienteId(Long pacienteId);
}
