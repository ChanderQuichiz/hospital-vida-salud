package com.hospital.atenciones.application.usecases;

import org.springframework.stereotype.Service;

import com.hospital.atenciones.application.dto.AtencionResponse;
import com.hospital.atenciones.domain.exceptions.AtencionNotFoundException;
import com.hospital.atenciones.domain.repositories.AtencionRepository;
import com.hospital.atenciones.domain.valueobjects.AtencionId;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ObtenerAtencionUseCase {

    private final AtencionRepository atencionRepository;

    public ObtenerAtencionUseCase(AtencionRepository atencionRepository) {
        this.atencionRepository = atencionRepository;
    }

    public Mono<AtencionResponse> execute(Long id) {
        return atencionRepository.findById(new AtencionId(id))
                .map(AtencionResponse::fromDomain)
                .switchIfEmpty(Mono.error(new AtencionNotFoundException(id)));
    }

    public Flux<AtencionResponse> executeListByPacienteId(Long pacienteId) {
        return atencionRepository.findByPacienteId(pacienteId).map(AtencionResponse::fromDomain);
    }
}
