package com.hospital.atenciones.domain.valueobjects;

public record CodigoAtencion(String value) {
    public CodigoAtencion {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("El codigo de atencion no puede estar vacio");
        }
    }
}
