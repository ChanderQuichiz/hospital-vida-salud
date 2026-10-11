package com.hospital.notificaciones.infrastructure.persistence;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import com.hospital.notificaciones.domain.entities.Notificacion;

@Table("notificacion")
public class NotificacionR2dbcEntity {

    @Id
    private Long id;

    @Column("evento_id")
    private String eventoId;

    @Column("paciente_id")
    private Long pacienteId;

    @Column("cita_id")
    private Long citaId;

    private String tipo;
    private String mensaje;

    @Column("fecha_envio")
    private LocalDateTime fechaEnvio;

    public NotificacionR2dbcEntity() {}

    public NotificacionR2dbcEntity(Long id, String eventoId, Long pacienteId, Long citaId, String tipo, String mensaje, LocalDateTime fechaEnvio) {
        this.id = id;
        this.eventoId = eventoId;
        this.pacienteId = pacienteId;
        this.citaId = citaId;
        this.tipo = tipo;
        this.mensaje = mensaje;
        this.fechaEnvio = fechaEnvio;
    }

    public static NotificacionR2dbcEntity fromDomain(Notificacion notificacion) {
        return new NotificacionR2dbcEntity(
            notificacion.getId(),
            notificacion.getEventoId(),
            notificacion.getPacienteId(),
            notificacion.getCitaId(),
            notificacion.getTipo(),
            notificacion.getMensaje(),
            notificacion.getFechaEnvio()
        );
    }

    public Notificacion toDomain() {
        return new Notificacion(this.id, this.eventoId, this.pacienteId, this.citaId, this.tipo, this.mensaje, this.fechaEnvio);
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getEventoId() { return eventoId; }
    public Long getPacienteId() { return pacienteId; }
    public Long getCitaId() { return citaId; }
    public String getTipo() { return tipo; }
    public String getMensaje() { return mensaje; }
    public LocalDateTime getFechaEnvio() { return fechaEnvio; }
}
