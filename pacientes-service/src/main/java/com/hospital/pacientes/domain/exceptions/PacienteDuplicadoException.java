package com.hospital.pacientes.domain.exceptions;

public class PacienteDuplicadoException extends RuntimeException {
    public PacienteDuplicadoException(String tipo, String numero) {
        super("Ya existe un paciente registrado con " + tipo + " " + numero);
    }
}
