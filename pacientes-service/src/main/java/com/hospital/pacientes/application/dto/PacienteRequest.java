package com.hospital.pacientes.application.dto;

import java.time.LocalDate;

public record PacienteRequest(
    String tipoDocumento,
    String numeroDocumento,
    String nombres,
    String apellidos,
    LocalDate fechaNacimiento,
    String telefono,
    String correo,
    String direccion
) {}
