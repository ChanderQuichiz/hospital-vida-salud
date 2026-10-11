package com.hospital.citas.application.usecases;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.hospital.citas.application.dto.CitaResponse;
import com.hospital.citas.application.dto.ReprogramarRequest;
import com.hospital.citas.application.ports.EventPublisherPort;
import com.hospital.citas.domain.exceptions.CitaNotFoundException;
import com.hospital.citas.domain.exceptions.ConflictException;
import com.hospital.citas.domain.repositories.CitaRepository;
import com.hospital.citas.domain.valueobjects.CitaId;

import reactor.core.publisher.Mono;

@Service
public class ReprogramarCitaUseCase {

    private final CitaRepository citaRepository;
    private final EventPublisherPort eventPublisherPort;

    public ReprogramarCitaUseCase(CitaRepository citaRepository, EventPublisherPort eventPublisherPort) {
        this.citaRepository = citaRepository;
        this.eventPublisherPort = eventPublisherPort;
    }

    public Mono<CitaResponse> execute(Long id, ReprogramarRequest request) {
        return citaRepository.findById(new CitaId(id))
                .switchIfEmpty(Mono.error(new CitaNotFoundException(id)))
                .flatMap(cita -> {
                    LocalDateTime fechaAnterior = cita.getFechaHora();

                    return citaRepository.existsChoqueProfesional(cita.getProfesionalCodigo(), request.nuevaFechaHora())
                            .flatMap(choqueProf -> {
                                if (choqueProf) {
                                    return Mono.error(new ConflictException("El profesional ya tiene cita en ese horario"));
                                }
                                return citaRepository.existsChoquePaciente(cita.getPacienteId(), request.nuevaFechaHora());
                            })
                            .flatMap(choquePac -> {
                                if (choquePac) {
                                    return Mono.error(new ConflictException("El paciente ya tiene cita en ese horario"));
                                }

                                cita.reprogramar(request.nuevaFechaHora());
                                return citaRepository.save(cita)
                                        .flatMap(guardada -> eventPublisherPort.publicarCitaReprogramada(guardada, fechaAnterior.toString()).thenReturn(guardada));
                            });
                })
                .map(CitaResponse::fromDomain);
    }
}
