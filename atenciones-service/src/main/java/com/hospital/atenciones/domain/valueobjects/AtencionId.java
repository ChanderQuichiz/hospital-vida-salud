package com.hospital.atenciones.domain.valueobjects;

public record AtencionId(Long value) {
    public AtencionId {
        if (value == null || value <= 0) {
            throw new IllegalArgumentException("AtencionId debe ser un numero entero positivo");
        }
    }
}
