package com.hospital.citas.application.ports;

import com.hospital.citas.application.dto.PacienteDTO;

import reactor.core.publisher.Mono;

public interface PacientePort {
    Mono<PacienteDTO> obtenerPaciente(Long pacienteId);
}
