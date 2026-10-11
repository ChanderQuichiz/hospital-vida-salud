package com.hospital.atenciones.application.usecases;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.hospital.atenciones.application.dto.AtencionRequest;
import com.hospital.atenciones.application.dto.AtencionResponse;
import com.hospital.atenciones.application.ports.CitaPort;
import com.hospital.atenciones.application.ports.EventPublisherPort;
import com.hospital.atenciones.domain.entities.Atencion;
import com.hospital.atenciones.domain.exceptions.ConflictException;
import com.hospital.atenciones.domain.repositories.AtencionRepository;
import com.hospital.atenciones.domain.valueobjects.CodigoAtencion;

import reactor.core.publisher.Mono;

@Service
public class RegistrarAtencionUseCase {

    private final AtencionRepository atencionRepository;
    private final CitaPort citaPort;
    private final EventPublisherPort eventPublisherPort;

    public RegistrarAtencionUseCase(AtencionRepository atencionRepository, CitaPort citaPort, EventPublisherPort eventPublisherPort) {
        this.atencionRepository = atencionRepository;
        this.citaPort = citaPort;
        this.eventPublisherPort = eventPublisherPort;
    }

    public Mono<AtencionResponse> execute(AtencionRequest request) {
        return atencionRepository.existsByCitaId(request.citaId())
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.error(new ConflictException("Ya existe una atencion registrada para la cita ID: " + request.citaId()));
                    }

                    return citaPort.obtenerCita(request.citaId())
                            .flatMap(cita -> {
                                if (!"CONFIRMADA".equalsIgnoreCase(cita.estado())) {
                                    return Mono.error(new ConflictException("Solo se pueden registrar atenciones para citas en estado CONFIRMADA. Estado actual: " + cita.estado()));
                                }

                                String codigo = "ATE-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
                                Atencion atencion = Atencion.create(
                                    new CodigoAtencion(codigo),
                                    cita.pacienteId(),
                                    cita.id(),
                                    request.area()
                                );

                                return atencionRepository.save(atencion)
                                        .flatMap(guardada -> eventPublisherPort.publicarAtencionRegistrada(guardada).thenReturn(guardada));
                            });
                })
                .map(AtencionResponse::fromDomain);
    }
}
