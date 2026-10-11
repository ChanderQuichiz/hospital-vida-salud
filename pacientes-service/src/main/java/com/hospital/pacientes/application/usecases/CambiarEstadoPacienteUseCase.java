package com.hospital.pacientes.application.usecases;

import org.springframework.stereotype.Service;

import com.hospital.pacientes.application.dto.PacienteResponse;
import com.hospital.pacientes.domain.exceptions.PacienteNotFoundException;
import com.hospital.pacientes.domain.repositories.PacienteRepository;
import com.hospital.pacientes.domain.valueobjects.EstadoPaciente;
import com.hospital.pacientes.domain.valueobjects.PacienteId;

import reactor.core.publisher.Mono;

@Service
public class CambiarEstadoPacienteUseCase {

    private final PacienteRepository pacienteRepository;

    public CambiarEstadoPacienteUseCase(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    public Mono<PacienteResponse> execute(Long id, String nuevoEstado) {
        EstadoPaciente estado = EstadoPaciente.fromString(nuevoEstado);
        return pacienteRepository.findById(new PacienteId(id))
                .switchIfEmpty(Mono.error(new PacienteNotFoundException(id)))
                .flatMap(paciente -> {
                    paciente.cambiarEstado(estado);
                    return pacienteRepository.save(paciente);
                })
                .map(PacienteResponse::fromDomain);
    }
}
