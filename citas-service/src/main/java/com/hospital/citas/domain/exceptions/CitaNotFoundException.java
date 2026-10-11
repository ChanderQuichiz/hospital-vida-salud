package com.hospital.citas.domain.exceptions;

public class CitaNotFoundException extends RuntimeException {
    public CitaNotFoundException(Long id) {
        super("Cita no encontrada con ID: " + id);
    }
}
