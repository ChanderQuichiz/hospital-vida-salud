package com.hospital.atenciones.application.ports;

import com.hospital.atenciones.application.dto.CitaDTO;

import reactor.core.publisher.Mono;

public interface CitaPort {
    Mono<CitaDTO> obtenerCita(Long citaId);
}
