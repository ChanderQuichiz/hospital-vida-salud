package com.hospital.atenciones.domain.entities;

import java.time.LocalDateTime;

import com.hospital.atenciones.domain.valueobjects.AtencionId;
import com.hospital.atenciones.domain.valueobjects.CodigoAtencion;
import com.hospital.atenciones.domain.valueobjects.EstadoAtencion;

public class Atencion {

    private AtencionId id;
    private CodigoAtencion codigo;
    private Long pacienteId;
    private Long citaId;
    private LocalDateTime fechaAtencion;
    private String area;
    private EstadoAtencion estado;

    public Atencion(AtencionId id, CodigoAtencion codigo, Long pacienteId, Long citaId, LocalDateTime fechaAtencion, String area, EstadoAtencion estado) {
        this.id = id;
        this.codigo = codigo;
        this.pacienteId = pacienteId;
        this.citaId = citaId;
        this.fechaAtencion = fechaAtencion != null ? fechaAtencion : LocalDateTime.now();
        this.area = area;
        this.estado = estado != null ? estado : EstadoAtencion.REGISTRADA;
    }

    public static Atencion create(CodigoAtencion codigo, Long pacienteId, Long citaId, String area) {
        return new Atencion(null, codigo, pacienteId, citaId, LocalDateTime.now(), area, EstadoAtencion.REGISTRADA);
    }

    public void anular() {
        if (this.estado == EstadoAtencion.ANULADA) {
            throw new IllegalStateException("La atencion ya se encuentra ANULADA");
        }
        this.estado = EstadoAtencion.ANULADA;
    }

    public AtencionId getId() { return id; }
    public CodigoAtencion getCodigo() { return codigo; }
    public Long getPacienteId() { return pacienteId; }
    public Long getCitaId() { return citaId; }
    public LocalDateTime getFechaAtencion() { return fechaAtencion; }
    public String getArea() { return area; }
    public EstadoAtencion getEstado() { return estado; }
}
