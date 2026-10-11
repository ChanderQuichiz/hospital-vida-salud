package com.hospital.pacientes.application.dto;

import java.time.LocalDate;

import com.hospital.pacientes.domain.entities.Paciente;

public record PacienteResponse(
    Long id,
    String tipoDocumento,
    String numeroDocumento,
    String nombres,
    String apellidos,
    LocalDate fechaNacimiento,
    String telefono,
    String correo,
    String direccion,
    String estado
) {
    public static PacienteResponse fromDomain(Paciente paciente) {
        return new PacienteResponse(
            paciente.getId() != null ? paciente.getId().value() : null,
            paciente.getDocumento().tipo(),
            paciente.getDocumento().numero(),
            paciente.getNombres(),
            paciente.getApellidos(),
            paciente.getFechaNacimiento(),
            paciente.getTelefono(),
            paciente.getCorreo(),
            paciente.getDireccion(),
            paciente.getEstado().name()
        );
    }
}
