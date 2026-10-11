package com.hospital.citas.application.usecases;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.hospital.citas.application.dto.CitaRequest;
import com.hospital.citas.application.dto.CitaResponse;
import com.hospital.citas.application.ports.PacientePort;
import com.hospital.citas.domain.entities.Cita;
import com.hospital.citas.domain.exceptions.ConflictException;
import com.hospital.citas.domain.repositories.CitaRepository;
import com.hospital.citas.domain.valueobjects.CodigoCita;

import reactor.core.publisher.Mono;

@Service
public class ProgramarCitaUseCase {

    private final CitaRepository citaRepository;
    private final PacientePort pacientePort;

    public ProgramarCitaUseCase(CitaRepository citaRepository, PacientePort pacientePort) {
        this.citaRepository = citaRepository;
        this.pacientePort = pacientePort;
    }

    public Mono<CitaResponse> execute(CitaRequest request) {
        return pacientePort.obtenerPaciente(request.pacienteId())
                .flatMap(paciente -> {
                    if (!"ACTIVO".equalsIgnoreCase(paciente.estado())) {
                        return Mono.error(new ConflictException("El paciente " + request.pacienteId() + " se encuentra INACTIVO"));
                    }

                    return citaRepository.existsChoqueProfesional(request.profesionalCodigo(), request.fechaHora())
                            .flatMap(choqueProf -> {
                                if (choqueProf) {
                                    return Mono.error(new ConflictException("El profesional " + request.profesionalCodigo() + " ya tiene una cita activa en ese horario"));
                                }
                                return citaRepository.existsChoquePaciente(request.pacienteId(), request.fechaHora());
                            })
                            .flatMap(choquePac -> {
                                if (choquePac) {
                                    return Mono.error(new ConflictException("El paciente ya tiene una cita activa en ese horario"));
                                }

                                String codigo = "CIT-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
                                Cita cita = Cita.create(
                                    new CodigoCita(codigo),
                                    request.pacienteId(),
                                    request.especialidad(),
                                    request.fechaHora(),
                                    request.profesionalCodigo(),
                                    request.motivo()
                                );

                                return citaRepository.save(cita);
                            });
                })
                .map(CitaResponse::fromDomain);
    }
}
