package com.hospital.atenciones.domain.valueobjects;

public enum EstadoAtencion {
    REGISTRADA,
    ANULADA;

    public static EstadoAtencion fromString(String value) {
        if (value == null || value.isBlank()) return REGISTRADA;
        try {
            return EstadoAtencion.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Estado invalido de atencion: " + value);
        }
    }
}
