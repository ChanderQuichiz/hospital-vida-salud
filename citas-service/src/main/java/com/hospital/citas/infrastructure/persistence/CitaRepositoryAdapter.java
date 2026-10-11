package com.hospital.citas.infrastructure.persistence;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.hospital.citas.domain.entities.Cita;
import com.hospital.citas.domain.repositories.CitaRepository;
import com.hospital.citas.domain.valueobjects.CitaId;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class CitaRepositoryAdapter implements CitaRepository {

    private final SpringDataCitaRepository repository;

    public CitaRepositoryAdapter(SpringDataCitaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Mono<Cita> save(Cita cita) {
        CitaR2dbcEntity entity = CitaR2dbcEntity.fromDomain(cita);
        return repository.save(entity).map(CitaR2dbcEntity::toDomain);
    }

    @Override
    public Mono<Cita> findById(CitaId id) {
        return repository.findById(id.value()).map(CitaR2dbcEntity::toDomain);
    }

    @Override
    public Flux<Cita> findByPacienteId(Long pacienteId) {
        return repository.findByPacienteId(pacienteId).map(CitaR2dbcEntity::toDomain);
    }

    @Override
    public Mono<Boolean> existsChoqueProfesional(String profesionalCodigo, LocalDateTime fechaHora) {
        return repository.existsChoqueProfesional(profesionalCodigo, fechaHora);
    }

    @Override
    public Mono<Boolean> existsChoquePaciente(Long pacienteId, LocalDateTime fechaHora) {
        return repository.existsChoquePaciente(pacienteId, fechaHora);
    }
}
