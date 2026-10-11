package com.hospital.atenciones.application.ports;

import com.hospital.atenciones.domain.entities.Atencion;

import reactor.core.publisher.Mono;

public interface EventPublisherPort {
    Mono<Void> publicarAtencionRegistrada(Atencion atencion);
}
