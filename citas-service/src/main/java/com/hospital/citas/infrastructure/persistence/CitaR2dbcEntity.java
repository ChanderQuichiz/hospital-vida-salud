package com.hospital.citas.infrastructure.persistence;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import com.hospital.citas.domain.entities.Cita;
import com.hospital.citas.domain.valueobjects.CitaId;
import com.hospital.citas.domain.valueobjects.CodigoCita;
import com.hospital.citas.domain.valueobjects.EstadoCita;

@Table("cita")
public class CitaR2dbcEntity {

    @Id
    private Long id;

    private String codigo;

    @Column("paciente_id")
    private Long pacienteId;

    private String especialidad;

    @Column("fecha_hora")
    private LocalDateTime fechaHora;

    @Column("profesional_codigo")
    private String profesionalCodigo;

    private String motivo;
    private String estado;

    @Column("fecha_creacion")
    private LocalDateTime fechaCreacion;

    @Column("fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

    public CitaR2dbcEntity() {}

    public CitaR2dbcEntity(Long id, String codigo, Long pacienteId, String especialidad, LocalDateTime fechaHora, String profesionalCodigo, String motivo, String estado, LocalDateTime fechaCreacion, LocalDateTime fechaActualizacion) {
        this.id = id;
        this.codigo = codigo;
        this.pacienteId = pacienteId;
        this.especialidad = especialidad;
        this.fechaHora = fechaHora;
        this.profesionalCodigo = profesionalCodigo;
        this.motivo = motivo;
        this.estado = estado;
        this.fechaCreacion = fechaCreacion;
        this.fechaActualizacion = fechaActualizacion;
    }

    public static CitaR2dbcEntity fromDomain(Cita cita) {
        return new CitaR2dbcEntity(
            cita.getId() != null ? cita.getId().value() : null,
            cita.getCodigo().value(),
            cita.getPacienteId(),
            cita.getEspecialidad(),
            cita.getFechaHora(),
            cita.getProfesionalCodigo(),
            cita.getMotivo(),
            cita.getEstado().name(),
            cita.getFechaCreacion(),
            cita.getFechaActualizacion()
        );
    }

    public Cita toDomain() {
        return new Cita(
            new CitaId(this.id),
            new CodigoCita(this.codigo),
            this.pacienteId,
            this.especialidad,
            this.fechaHora,
            this.profesionalCodigo,
            this.motivo,
            EstadoCita.fromString(this.estado),
            this.fechaCreacion,
            this.fechaActualizacion
        );
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCodigo() { return codigo; }
    public Long getPacienteId() { return pacienteId; }
    public String getEspecialidad() { return especialidad; }
    public LocalDateTime getFechaHora() { return fechaHora; }
    public String getProfesionalCodigo() { return profesionalCodigo; }
    public String getMotivo() { return motivo; }
    public String getEstado() { return estado; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public LocalDateTime getFechaActualizacion() { return fechaActualizacion; }
}
