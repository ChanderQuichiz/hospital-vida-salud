package com.hospital.citas.application.usecases;

import org.springframework.stereotype.Service;

import com.hospital.citas.application.dto.CitaResponse;
import com.hospital.citas.application.ports.EventPublisherPort;
import com.hospital.citas.domain.exceptions.CitaNotFoundException;
import com.hospital.citas.domain.repositories.CitaRepository;
import com.hospital.citas.domain.valueobjects.CitaId;

import reactor.core.publisher.Mono;

@Service
public class ConfirmarCitaUseCase {

    private final CitaRepository citaRepository;
    private final EventPublisherPort eventPublisherPort;

    public ConfirmarCitaUseCase(CitaRepository citaRepository, EventPublisherPort eventPublisherPort) {
        this.citaRepository = citaRepository;
        this.eventPublisherPort = eventPublisherPort;
    }

    public Mono<CitaResponse> execute(Long id) {
        return citaRepository.findById(new CitaId(id))
                .switchIfEmpty(Mono.error(new CitaNotFoundException(id)))
                .flatMap(cita -> {
                    cita.confirmar();
                    return citaRepository.save(cita)
                            .flatMap(guardada -> eventPublisherPort.publicarCitaConfirmada(guardada).thenReturn(guardada));
                })
                .map(CitaResponse::fromDomain);
    }
}
