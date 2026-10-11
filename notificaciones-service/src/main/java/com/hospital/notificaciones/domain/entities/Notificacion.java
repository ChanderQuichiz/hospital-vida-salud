package com.hospital.notificaciones.domain.entities;

import java.time.LocalDateTime;

public class Notificacion {

    private Long id;
    private String eventoId;
    private Long pacienteId;
    private Long citaId;
    private String tipo;
    private String mensaje;
    private LocalDateTime fechaEnvio;

    public Notificacion(Long id, String eventoId, Long pacienteId, Long citaId, String tipo, String mensaje, LocalDateTime fechaEnvio) {
        this.id = id;
        this.eventoId = eventoId;
        this.pacienteId = pacienteId;
        this.citaId = citaId;
        this.tipo = tipo;
        this.mensaje = mensaje;
        this.fechaEnvio = fechaEnvio != null ? fechaEnvio : LocalDateTime.now();
    }

    public static Notificacion create(String eventoId, Long pacienteId, Long citaId, String tipo, String mensaje) {
        return new Notificacion(null, eventoId, pacienteId, citaId, tipo, mensaje, LocalDateTime.now());
    }

    public Long getId() { return id; }
    public String getEventoId() { return eventoId; }
    public Long getPacienteId() { return pacienteId; }
    public Long getCitaId() { return citaId; }
    public String getTipo() { return tipo; }
    public String getMensaje() { return mensaje; }
    public LocalDateTime getFechaEnvio() { return fechaEnvio; }
}
