package com.hospital.auditoria.application.usecases;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.hospital.auditoria.domain.entities.AuditoriaLog;
import com.hospital.auditoria.domain.repositories.AuditoriaRepository;

import reactor.core.publisher.Mono;

@Service
public class RegistrarAuditoriaUseCase {

    private static final Logger log = LoggerFactory.getLogger(RegistrarAuditoriaUseCase.class);

    private final AuditoriaRepository auditoriaRepository;

    public RegistrarAuditoriaUseCase(AuditoriaRepository auditoriaRepository) {
        this.auditoriaRepository = auditoriaRepository;
    }

    public Mono<Void> execute(Map<String, Object> evento) {
        try {
            String eventoId = evento.get("eventoId").toString();
            String tipoEvento = evento.get("tipo").toString();
            String origen = evento.get("origen").toString();
            String recurso = evento.get("recurso").toString();
            Long recursoId = Long.valueOf(evento.get("recursoId").toString());
            String estadoAnterior = evento.get("estadoAnterior") != null ? evento.get("estadoAnterior").toString() : "N/A";
            String estadoNuevo = evento.get("estadoNuevo") != null ? evento.get("estadoNuevo").toString() : "N/A";

            AuditoriaLog logEntry = AuditoriaLog.create(eventoId, tipoEvento, recurso, recursoId, estadoAnterior, estadoNuevo, origen);

            return auditoriaRepository.save(logEntry)
                    .doOnSuccess(saved -> log.info("Auditoria registrada exitosamente para eventoId: {}", eventoId))
                    .onErrorResume(ex -> {
                        log.warn("Evento duplicado o ya procesado en Auditoria (eventoId: {})", eventoId);
                        return Mono.empty();
                    })
                    .then();
        } catch (Exception e) {
            log.error("Error registrando auditoria: {}", e.getMessage());
            return Mono.empty();
        }
    }
}
