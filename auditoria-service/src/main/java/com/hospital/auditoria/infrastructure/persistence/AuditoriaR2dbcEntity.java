package com.hospital.auditoria.infrastructure.persistence;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import com.hospital.auditoria.domain.entities.AuditoriaLog;

@Table("auditoria")
public class AuditoriaR2dbcEntity {

    @Id
    private Long id;

    @Column("evento_id")
    private String eventoId;

    @Column("tipo_evento")
    private String tipoEvento;

    private String recurso;

    @Column("recurso_id")
    private Long recursoId;

    @Column("estado_anterior")
    private String estadoAnterior;

    @Column("estado_nuevo")
    private String estadoNuevo;

    private String origen;
    private LocalDateTime fecha;

    public AuditoriaR2dbcEntity() {}

    public AuditoriaR2dbcEntity(Long id, String eventoId, String tipoEvento, String recurso, Long recursoId, String estadoAnterior, String estadoNuevo, String origen, LocalDateTime fecha) {
        this.id = id;
        this.eventoId = eventoId;
        this.tipoEvento = tipoEvento;
        this.recurso = recurso;
        this.recursoId = recursoId;
        this.estadoAnterior = estadoAnterior;
        this.estadoNuevo = estadoNuevo;
        this.origen = origen;
        this.fecha = fecha;
    }

    public static AuditoriaR2dbcEntity fromDomain(AuditoriaLog log) {
        return new AuditoriaR2dbcEntity(
            log.getId(),
            log.getEventoId(),
            log.getTipoEvento(),
            log.getRecurso(),
            log.getRecursoId(),
            log.getEstadoAnterior(),
            log.getEstadoNuevo(),
            log.getOrigen(),
            log.getFecha()
        );
    }

    public AuditoriaLog toDomain() {
        return new AuditoriaLog(this.id, this.eventoId, this.tipoEvento, this.recurso, this.recursoId, this.estadoAnterior, this.estadoNuevo, this.origen, this.fecha);
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getEventoId() { return eventoId; }
    public String getTipoEvento() { return tipoEvento; }
    public String getRecurso() { return recurso; }
    public Long getRecursoId() { return recursoId; }
    public String getEstadoAnterior() { return estadoAnterior; }
    public String getEstadoNuevo() { return estadoNuevo; }
    public String getOrigen() { return origen; }
    public LocalDateTime getFecha() { return fecha; }
}
