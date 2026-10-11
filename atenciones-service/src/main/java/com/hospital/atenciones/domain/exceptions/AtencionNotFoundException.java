package com.hospital.atenciones.domain.exceptions;

public class AtencionNotFoundException extends RuntimeException {
    public AtencionNotFoundException(Long id) {
        super("Atencion no encontrada con ID: " + id);
    }
}
