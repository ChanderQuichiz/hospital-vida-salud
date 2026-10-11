package com.hospital.pacientes.infrastructure.persistence;

import org.springframework.stereotype.Component;

import com.hospital.pacientes.domain.entities.Paciente;
import com.hospital.pacientes.domain.repositories.PacienteRepository;
import com.hospital.pacientes.domain.valueobjects.Documento;
import com.hospital.pacientes.domain.valueobjects.PacienteId;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class PacienteRepositoryAdapter implements PacienteRepository {

    private final SpringDataPacienteRepository repository;

    public PacienteRepositoryAdapter(SpringDataPacienteRepository repository) {
        this.repository = repository;
    }

    @Override
    public Mono<Paciente> save(Paciente paciente) {
        PacienteR2dbcEntity entity = PacienteR2dbcEntity.fromDomain(paciente);
        return repository.save(entity).map(PacienteR2dbcEntity::toDomain);
    }

    @Override
    public Mono<Paciente> findById(PacienteId id) {
        return repository.findById(id.value()).map(PacienteR2dbcEntity::toDomain);
    }

    @Override
    public Mono<Boolean> existsByDocumento(Documento documento) {
        return repository.existsByTipoDocumentoAndNumeroDocumento(documento.tipo(), documento.numero());
    }

    @Override
    public Flux<Paciente> findAll() {
        return repository.findAll().map(PacienteR2dbcEntity::toDomain);
    }
}
