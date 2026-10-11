package com.hospital.atenciones.application.usecases;

import org.springframework.stereotype.Service;

import com.hospital.atenciones.application.dto.AtencionResponse;
import com.hospital.atenciones.domain.exceptions.AtencionNotFoundException;
import com.hospital.atenciones.domain.repositories.AtencionRepository;
import com.hospital.atenciones.domain.valueobjects.AtencionId;

import reactor.core.publisher.Mono;

@Service
public class AnularAtencionUseCase {

    private final AtencionRepository atencionRepository;

    public AnularAtencionUseCase(AtencionRepository atencionRepository) {
        this.atencionRepository = atencionRepository;
    }

    public Mono<AtencionResponse> execute(Long id) {
        return atencionRepository.findById(new AtencionId(id))
                .switchIfEmpty(Mono.error(new AtencionNotFoundException(id)))
                .flatMap(atencion -> {
                    atencion.anular();
                    return atencionRepository.save(atencion);
                })
                .map(AtencionResponse::fromDomain);
    }
}
