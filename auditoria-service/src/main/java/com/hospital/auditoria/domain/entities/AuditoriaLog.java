package com.hospital.auditoria.domain.entities;

import java.time.LocalDateTime;

public class AuditoriaLog {

    private Long id;
    private String eventoId;
    private String tipoEvento;
    private String recurso;
    private Long recursoId;
    private String estadoAnterior;
    private String estadoNuevo;
    private String origen;
    private LocalDateTime fecha;

    public AuditoriaLog(Long id, String eventoId, String tipoEvento, String recurso, Long recursoId, String estadoAnterior, String estadoNuevo, String origen, LocalDateTime fecha) {
        this.id = id;
        this.eventoId = eventoId;
        this.tipoEvento = tipoEvento;
        this.recurso = recurso;
        this.recursoId = recursoId;
        this.estadoAnterior = estadoAnterior;
        this.estadoNuevo = estadoNuevo;
        this.origen = origen;
        this.fecha = fecha != null ? fecha : LocalDateTime.now();
    }

    public static AuditoriaLog create(String eventoId, String tipoEvento, String recurso, Long recursoId, String estadoAnterior, String estadoNuevo, String origen) {
        return new AuditoriaLog(null, eventoId, tipoEvento, recurso, recursoId, estadoAnterior, estadoNuevo, origen, LocalDateTime.now());
    }

    public Long getId() { return id; }
    public String getEventoId() { return eventoId; }
    public String getTipoEvento() { return tipoEvento; }
    public String getRecurso() { return recurso; }
    public Long getRecursoId() { return recursoId; }
    public String getEstadoAnterior() { return estadoAnterior; }
    public String getEstadoNuevo() { return estadoNuevo; }
    public String getOrigen() { return origen; }
    public LocalDateTime getFecha() { return fecha; }
}
