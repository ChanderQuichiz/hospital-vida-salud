package com.hospital.citas.domain.valueobjects;

public record CodigoCita(String value) {
    public CodigoCita {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("El codigo de cita no puede estar vacio");
        }
    }
}
