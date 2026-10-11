package com.hospital.atenciones.infrastructure.persistence;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import com.hospital.atenciones.domain.entities.Atencion;
import com.hospital.atenciones.domain.valueobjects.AtencionId;
import com.hospital.atenciones.domain.valueobjects.CodigoAtencion;
import com.hospital.atenciones.domain.valueobjects.EstadoAtencion;

@Table("atencion")
public class AtencionR2dbcEntity {

    @Id
    private Long id;

    private String codigo;

    @Column("paciente_id")
    private Long pacienteId;

    @Column("cita_id")
    private Long citaId;

    @Column("fecha_atencion")
    private LocalDateTime fechaAtencion;

    private String area;
    private String estado;

    public AtencionR2dbcEntity() {}

    public AtencionR2dbcEntity(Long id, String codigo, Long pacienteId, Long citaId, LocalDateTime fechaAtencion, String area, String estado) {
        this.id = id;
        this.codigo = codigo;
        this.pacienteId = pacienteId;
        this.citaId = citaId;
        this.fechaAtencion = fechaAtencion;
        this.area = area;
        this.estado = estado;
    }

    public static AtencionR2dbcEntity fromDomain(Atencion atencion) {
        return new AtencionR2dbcEntity(
            atencion.getId() != null ? atencion.getId().value() : null,
            atencion.getCodigo().value(),
            atencion.getPacienteId(),
            atencion.getCitaId(),
            atencion.getFechaAtencion(),
            atencion.getArea(),
            atencion.getEstado().name()
        );
    }

    public Atencion toDomain() {
        return new Atencion(
            new AtencionId(this.id),
            new CodigoAtencion(this.codigo),
            this.pacienteId,
            this.citaId,
            this.fechaAtencion,
            this.area,
            EstadoAtencion.fromString(this.estado)
        );
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCodigo() { return codigo; }
    public Long getPacienteId() { return pacienteId; }
    public Long getCitaId() { return citaId; }
    public LocalDateTime getFechaAtencion() { return fechaAtencion; }
    public String getArea() { return area; }
    public String getEstado() { return estado; }
}
