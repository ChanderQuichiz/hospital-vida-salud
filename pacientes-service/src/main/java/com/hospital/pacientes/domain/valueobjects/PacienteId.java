package com.hospital.pacientes.domain.valueobjects;

public record PacienteId(Long value) {
    public PacienteId {
        if (value == null || value <= 0) {
            throw new IllegalArgumentException("PacienteId debe ser un ID valido positivo");
        }
    }
}
