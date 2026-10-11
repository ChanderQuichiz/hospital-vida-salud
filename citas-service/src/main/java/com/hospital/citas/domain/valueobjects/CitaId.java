package com.hospital.citas.domain.valueobjects;

public record CitaId(Long value) {
    public CitaId {
        if (value == null || value <= 0) {
            throw new IllegalArgumentException("CitaId debe ser un numero positivo");
        }
    }
}
