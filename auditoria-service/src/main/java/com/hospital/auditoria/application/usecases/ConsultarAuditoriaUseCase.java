package com.hospital.auditoria.application.usecases;

import org.springframework.stereotype.Service;

import com.hospital.auditoria.application.dto.AuditoriaResponse;
import com.hospital.auditoria.domain.repositories.AuditoriaRepository;

import reactor.core.publisher.Flux;

@Service
public class ConsultarAuditoriaUseCase {

    private final AuditoriaRepository auditoriaRepository;

    public ConsultarAuditoriaUseCase(AuditoriaRepository auditoriaRepository) {
        this.auditoriaRepository = auditoriaRepository;
    }

    public Flux<AuditoriaResponse> execute(String recurso, Long recursoId) {
        return auditoriaRepository.findByRecursoAndRecursoId(recurso, recursoId)
                .map(AuditoriaResponse::fromDomain);
    }
}
