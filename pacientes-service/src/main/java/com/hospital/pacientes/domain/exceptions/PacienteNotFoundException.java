package com.hospital.pacientes.domain.exceptions;

public class PacienteNotFoundException extends RuntimeException {
    public PacienteNotFoundException(Long id) {
        super("Paciente no encontrado con ID: " + id);
    }
}
