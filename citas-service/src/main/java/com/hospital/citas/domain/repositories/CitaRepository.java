package com.hospital.citas.domain.repositories;

import java.time.LocalDateTime;

import com.hospital.citas.domain.entities.Cita;
import com.hospital.citas.domain.valueobjects.CitaId;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CitaRepository {
    Mono<Cita> save(Cita cita);
    Mono<Cita> findById(CitaId id);
    Flux<Cita> findByPacienteId(Long pacienteId);
    Mono<Boolean> existsChoqueProfesional(String profesionalCodigo, LocalDateTime fechaHora);
    Mono<Boolean> existsChoquePaciente(Long pacienteId, LocalDateTime fechaHora);
}
