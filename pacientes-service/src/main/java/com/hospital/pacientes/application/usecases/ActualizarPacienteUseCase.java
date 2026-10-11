package com.hospital.pacientes.application.usecases;

import org.springframework.stereotype.Service;

import com.hospital.pacientes.application.dto.PacienteRequest;
import com.hospital.pacientes.application.dto.PacienteResponse;
import com.hospital.pacientes.domain.exceptions.PacienteNotFoundException;
import com.hospital.pacientes.domain.repositories.PacienteRepository;
import com.hospital.pacientes.domain.valueobjects.PacienteId;

import reactor.core.publisher.Mono;

@Service
public class ActualizarPacienteUseCase {

    private final PacienteRepository pacienteRepository;

    public ActualizarPacienteUseCase(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    public Mono<PacienteResponse> execute(Long id, PacienteRequest request) {
        return pacienteRepository.findById(new PacienteId(id))
                .switchIfEmpty(Mono.error(new PacienteNotFoundException(id)))
                .flatMap(paciente -> {
                    paciente.actualizarDatos(request.nombres(), request.apellidos(), request.telefono(), request.correo(), request.direccion());
                    return pacienteRepository.save(paciente);
                })
                .map(PacienteResponse::fromDomain);
    }
}
