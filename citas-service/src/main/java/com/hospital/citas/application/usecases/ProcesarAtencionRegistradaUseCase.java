package com.hospital.citas.application.usecases;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.hospital.citas.domain.repositories.CitaRepository;
import com.hospital.citas.domain.valueobjects.CitaId;

import reactor.core.publisher.Mono;

@Service
public class ProcesarAtencionRegistradaUseCase {

    private static final Logger log = LoggerFactory.getLogger(ProcesarAtencionRegistradaUseCase.class);

    private final CitaRepository citaRepository;

    public ProcesarAtencionRegistradaUseCase(CitaRepository citaRepository) {
        this.citaRepository = citaRepository;
    }

    public Mono<Void> execute(Long citaId) {
        log.info("Procesando evento AtencionRegistrada para cita ID: {}", citaId);
        return citaRepository.findById(new CitaId(citaId))
                .flatMap(cita -> {
                    cita.marcarAtendida();
                    return citaRepository.save(cita);
                })
                .then();
    }
}
