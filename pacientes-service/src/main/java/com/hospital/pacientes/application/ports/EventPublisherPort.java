package com.hospital.pacientes.application.ports;

import com.hospital.pacientes.domain.entities.Paciente;

import reactor.core.publisher.Mono;

public interface EventPublisherPort {
    Mono<Void> publicarPacienteRegistrado(Paciente paciente);
}
