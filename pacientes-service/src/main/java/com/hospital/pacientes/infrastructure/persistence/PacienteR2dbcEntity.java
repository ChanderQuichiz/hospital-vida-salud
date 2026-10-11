package com.hospital.pacientes.infrastructure.persistence;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import com.hospital.pacientes.domain.entities.Paciente;
import com.hospital.pacientes.domain.valueobjects.Documento;
import com.hospital.pacientes.domain.valueobjects.EstadoPaciente;
import com.hospital.pacientes.domain.valueobjects.PacienteId;

@Table("paciente")
public class PacienteR2dbcEntity {

    @Id
    private Long id;

    @Column("tipo_documento")
    private String tipoDocumento;

    @Column("numero_documento")
    private String numeroDocumento;

    private String nombres;
    private String apellidos;

    @Column("fecha_nacimiento")
    private LocalDate fechaNacimiento;

    private String telefono;
    private String correo;
    private String direccion;

    @Column("fecha_registro")
    private LocalDateTime fechaRegistro;

    private String estado;

    public PacienteR2dbcEntity() {}

    public PacienteR2dbcEntity(Long id, String tipoDocumento, String numeroDocumento, String nombres, String apellidos, LocalDate fechaNacimiento, String telefono, String correo, String direccion, LocalDateTime fechaRegistro, String estado) {
        this.id = id;
        this.tipoDocumento = tipoDocumento;
        this.numeroDocumento = numeroDocumento;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.fechaNacimiento = fechaNacimiento;
        this.telefono = telefono;
        this.correo = correo;
        this.direccion = direccion;
        this.fechaRegistro = fechaRegistro;
        this.estado = estado;
    }

    public static PacienteR2dbcEntity fromDomain(Paciente paciente) {
        return new PacienteR2dbcEntity(
            paciente.getId() != null ? paciente.getId().value() : null,
            paciente.getDocumento().tipo(),
            paciente.getDocumento().numero(),
            paciente.getNombres(),
            paciente.getApellidos(),
            paciente.getFechaNacimiento(),
            paciente.getTelefono(),
            paciente.getCorreo(),
            paciente.getDireccion(),
            paciente.getFechaRegistro(),
            paciente.getEstado().name()
        );
    }

    public Paciente toDomain() {
        return new Paciente(
            new PacienteId(this.id),
            new Documento(this.tipoDocumento, this.numeroDocumento),
            this.nombres,
            this.apellidos,
            this.fechaNacimiento,
            this.telefono,
            this.correo,
            this.direccion,
            this.fechaRegistro,
            EstadoPaciente.fromString(this.estado)
        );
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTipoDocumento() { return tipoDocumento; }
    public String getNumeroDocumento() { return numeroDocumento; }
    public String getNombres() { return nombres; }
    public String getApellidos() { return apellidos; }
    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public String getTelefono() { return telefono; }
    public String getCorreo() { return correo; }
    public String getDireccion() { return direccion; }
    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public String getEstado() { return estado; }
}
