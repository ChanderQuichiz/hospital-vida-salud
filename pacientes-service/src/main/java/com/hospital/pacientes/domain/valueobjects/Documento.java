package com.hospital.pacientes.domain.valueobjects;

public record Documento(String tipo, String numero) {
    public Documento {
        if (tipo == null || tipo.isBlank() || numero == null || numero.isBlank()) {
            throw new IllegalArgumentException("Tipo y numero de documento son obligatorios");
        }
    }
}
