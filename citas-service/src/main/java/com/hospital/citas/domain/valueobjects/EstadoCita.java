package com.hospital.citas.domain.valueobjects;

public enum EstadoCita {
    PENDIENTE,
    CONFIRMADA,
    ATENDIDA,
    CANCELADA,
    AUSENTE;

    public static EstadoCita fromString(String value) {
        if (value == null || value.isBlank()) return PENDIENTE;
        try {
            return EstadoCita.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Estado invalido de cita: " + value);
        }
    }
}
