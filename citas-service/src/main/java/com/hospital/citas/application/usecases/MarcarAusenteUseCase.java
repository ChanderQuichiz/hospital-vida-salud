package com.hospital.citas.application.usecases;

import org.springframework.stereotype.Service;

import com.hospital.citas.application.dto.CitaResponse;
import com.hospital.citas.domain.exceptions.CitaNotFoundException;
import com.hospital.citas.domain.repositories.CitaRepository;
import com.hospital.citas.domain.valueobjects.CitaId;

import reactor.core.publisher.Mono;

@Service
public class MarcarAusenteUseCase {

    private final CitaRepository citaRepository;

    public MarcarAusenteUseCase(CitaRepository citaRepository) {
        this.citaRepository = citaRepository;
    }

    public Mono<CitaResponse> execute(Long id) {
        return citaRepository.findById(new CitaId(id))
                .switchIfEmpty(Mono.error(new CitaNotFoundException(id)))
                .flatMap(cita -> {
                    cita.marcarAusente();
                    return citaRepository.save(cita);
                })
                .map(CitaResponse::fromDomain);
    }
}
