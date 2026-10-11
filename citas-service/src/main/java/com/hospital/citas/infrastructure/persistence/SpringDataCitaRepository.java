package com.hospital.citas.infrastructure.persistence;

import java.time.LocalDateTime;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface SpringDataCitaRepository extends ReactiveCrudRepository<CitaR2dbcEntity, Long> {

    Flux<CitaR2dbcEntity> findByPacienteId(Long pacienteId);

    @Query("SELECT COUNT(*) > 0 FROM cita WHERE profesional_codigo = :profesionalCodigo AND fecha_hora = :fechaHora AND estado IN ('PENDIENTE', 'CONFIRMADA')")
    Mono<Boolean> existsChoqueProfesional(String profesionalCodigo, LocalDateTime fechaHora);

    @Query("SELECT COUNT(*) > 0 FROM cita WHERE paciente_id = :pacienteId AND fecha_hora = :fechaHora AND estado IN ('PENDIENTE', 'CONFIRMADA')")
    Mono<Boolean> existsChoquePaciente(Long pacienteId, LocalDateTime fechaHora);
}
