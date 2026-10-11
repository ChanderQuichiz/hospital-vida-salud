package com.hospital.pacientes.application.usecases;

import org.springframework.stereotype.Service;

import com.hospital.pacientes.application.dto.PacienteResponse;
import com.hospital.pacientes.domain.exceptions.PacienteNotFoundException;
import com.hospital.pacientes.domain.repositories.PacienteRepository;
import com.hospital.pacientes.domain.valueobjects.PacienteId;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ObtenerPacienteUseCase {

    private final PacienteRepository pacienteRepository;

    public ObtenerPacienteUseCase(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    public Mono<PacienteResponse> execute(Long id) {
        return pacienteRepository.findById(new PacienteId(id))
                .map(PacienteResponse::fromDomain)
                .switchIfEmpty(Mono.error(new PacienteNotFoundException(id)));
    }

    public Flux<PacienteResponse> executeListAll() {
        return pacienteRepository.findAll().map(PacienteResponse::fromDomain);
    }
}
