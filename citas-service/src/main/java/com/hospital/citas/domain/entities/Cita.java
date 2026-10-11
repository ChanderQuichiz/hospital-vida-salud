package com.hospital.citas.domain.entities;

import java.time.LocalDateTime;

import com.hospital.citas.domain.valueobjects.CitaId;
import com.hospital.citas.domain.valueobjects.CodigoCita;
import com.hospital.citas.domain.valueobjects.EstadoCita;

public class Cita {

    private CitaId id;
    private CodigoCita codigo;
    private Long pacienteId;
    private String especialidad;
    private LocalDateTime fechaHora;
    private String profesionalCodigo;
    private String motivo;
    private EstadoCita estado;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;

    public Cita(CitaId id, CodigoCita codigo, Long pacienteId, String especialidad, LocalDateTime fechaHora, String profesionalCodigo, String motivo, EstadoCita estado, LocalDateTime fechaCreacion, LocalDateTime fechaActualizacion) {
        this.id = id;
        this.codigo = codigo;
        this.pacienteId = pacienteId;
        this.especialidad = especialidad;
        this.fechaHora = fechaHora;
        this.profesionalCodigo = profesionalCodigo;
        this.motivo = motivo;
        this.estado = estado != null ? estado : EstadoCita.PENDIENTE;
        this.fechaCreacion = fechaCreacion != null ? fechaCreacion : LocalDateTime.now();
        this.fechaActualizacion = fechaActualizacion != null ? fechaActualizacion : LocalDateTime.now();
    }

    public static Cita create(CodigoCita codigo, Long pacienteId, String especialidad, LocalDateTime fechaHora, String profesionalCodigo, String motivo) {
        if (fechaHora == null || fechaHora.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("La fecha de la cita debe ser futura");
        }
        if (fechaHora.getMinute() % 30 != 0 || fechaHora.getSecond() != 0) {
            throw new IllegalArgumentException("Las citas solo se programan en bloques exactos de 30 minutos");
        }
        return new Cita(null, codigo, pacienteId, especialidad, fechaHora, profesionalCodigo, motivo, EstadoCita.PENDIENTE, LocalDateTime.now(), LocalDateTime.now());
    }

    public void confirmar() {
        if (this.estado != EstadoCita.PENDIENTE) {
            throw new IllegalStateException("Solo se puede confirmar una cita en estado PENDIENTE. Estado actual: " + this.estado);
        }
        this.estado = EstadoCita.CONFIRMADA;
        this.fechaActualizacion = LocalDateTime.now();
    }

    public void reprogramar(LocalDateTime nuevaFechaHora) {
        if (this.estado != EstadoCita.PENDIENTE && this.estado != EstadoCita.CONFIRMADA) {
            throw new IllegalStateException("Solo se puede reprogramar una cita PENDIENTE o CONFIRMADA. Estado actual: " + this.estado);
        }
        if (nuevaFechaHora == null || nuevaFechaHora.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("La nueva fecha de la cita debe ser futura");
        }
        if (nuevaFechaHora.getMinute() % 30 != 0 || nuevaFechaHora.getSecond() != 0) {
            throw new IllegalArgumentException("Las citas solo se programan en bloques exactos de 30 minutos");
        }
        this.fechaHora = nuevaFechaHora;
        this.fechaActualizacion = LocalDateTime.now();
    }

    public void cancelar() {
        if (this.estado != EstadoCita.PENDIENTE && this.estado != EstadoCita.CONFIRMADA) {
            throw new IllegalStateException("Solo se puede cancelar una cita PENDIENTE o CONFIRMADA. Estado actual: " + this.estado);
        }
        this.estado = EstadoCita.CANCELADA;
        this.fechaActualizacion = LocalDateTime.now();
    }

    public void marcarAusente() {
        if (this.estado != EstadoCita.CONFIRMADA) {
            throw new IllegalStateException("Solo se puede marcar AUSENTE una cita CONFIRMADA. Estado actual: " + this.estado);
        }
        this.estado = EstadoCita.AUSENTE;
        this.fechaActualizacion = LocalDateTime.now();
    }

    public void marcarAtendida() {
        if (this.estado != EstadoCita.CONFIRMADA) {
            throw new IllegalStateException("Solo se puede marcar ATENDIDA una cita CONFIRMADA. Estado actual: " + this.estado);
        }
        this.estado = EstadoCita.ATENDIDA;
        this.fechaActualizacion = LocalDateTime.now();
    }

    public CitaId getId() { return id; }
    public CodigoCita getCodigo() { return codigo; }
    public Long getPacienteId() { return pacienteId; }
    public String getEspecialidad() { return especialidad; }
    public LocalDateTime getFechaHora() { return fechaHora; }
    public String getProfesionalCodigo() { return profesionalCodigo; }
    public String getMotivo() { return motivo; }
    public EstadoCita getEstado() { return estado; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public LocalDateTime getFechaActualizacion() { return fechaActualizacion; }
}
