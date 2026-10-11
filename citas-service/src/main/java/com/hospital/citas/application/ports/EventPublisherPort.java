package com.hospital.citas.application.ports;

import com.hospital.citas.domain.entities.Cita;

import reactor.core.publisher.Mono;

public interface EventPublisherPort {
    Mono<Void> publicarCitaConfirmada(Cita cita);
    Mono<Void> publicarCitaReprogramada(Cita cita, String fechaAnterior);
    Mono<Void> publicarCitaCancelada(Cita cita);
}
