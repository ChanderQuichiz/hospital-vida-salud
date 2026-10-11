package com.hospital.pacientes.domain.repositories;

import com.hospital.pacientes.domain.entities.Paciente;
import com.hospital.pacientes.domain.valueobjects.Documento;
import com.hospital.pacientes.domain.valueobjects.PacienteId;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PacienteRepository {
    Mono<Paciente> save(Paciente paciente);
    Mono<Paciente> findById(PacienteId id);
    Mono<Boolean> existsByDocumento(Documento documento);
    Flux<Paciente> findAll();
}
