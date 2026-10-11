package com.hospital.pacientes.domain.valueobjects;

public enum EstadoPaciente {
    ACTIVO,
    INACTIVO;

    public static EstadoPaciente fromString(String value) {
        if (value == null || value.isBlank()) return ACTIVO;
        try {
            return EstadoPaciente.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Estado invalido: " + value + ". Permitos: ACTIVO, INACTIVO");
        }
    }
}
