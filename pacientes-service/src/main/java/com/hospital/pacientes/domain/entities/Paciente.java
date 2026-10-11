package com.hospital.pacientes.domain.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.hospital.pacientes.domain.valueobjects.Documento;
import com.hospital.pacientes.domain.valueobjects.EstadoPaciente;
import com.hospital.pacientes.domain.valueobjects.PacienteId;

public class Paciente {

    private PacienteId id;
    private Documento documento;
    private String nombres;
    private String apellidos;
    private LocalDate fechaNacimiento;
    private String telefono;
    private String correo;
    private String direccion;
    private LocalDateTime fechaRegistro;
    private EstadoPaciente estado;

    public Paciente(PacienteId id, Documento documento, String nombres, String apellidos, LocalDate fechaNacimiento, String telefono, String correo, String direccion, LocalDateTime fechaRegistro, EstadoPaciente estado) {
        this.id = id;
        this.documento = documento;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.fechaNacimiento = fechaNacimiento;
        this.telefono = telefono;
        this.correo = correo;
        this.direccion = direccion;
        this.fechaRegistro = fechaRegistro != null ? fechaRegistro : LocalDateTime.now();
        this.estado = estado != null ? estado : EstadoPaciente.ACTIVO;
    }

    public static Paciente create(Documento documento, String nombres, String apellidos, LocalDate fechaNacimiento, String telefono, String correo, String direccion) {
        return new Paciente(null, documento, nombres, apellidos, fechaNacimiento, telefono, correo, direccion, LocalDateTime.now(), EstadoPaciente.ACTIVO);
    }

    public void actualizarDatos(String nombres, String apellidos, String telefono, String correo, String direccion) {
        if (nombres != null && !nombres.isBlank()) this.nombres = nombres;
        if (apellidos != null && !apellidos.isBlank()) this.apellidos = apellidos;
        if (telefono != null && !telefono.isBlank()) this.telefono = telefono;
        if (correo != null && !correo.isBlank()) this.correo = correo;
        this.direccion = direccion;
    }

    public void cambiarEstado(EstadoPaciente nuevoEstado) {
        this.estado = nuevoEstado;
    }

    public PacienteId getId() { return id; }
    public Documento getDocumento() { return documento; }
    public String getNombres() { return nombres; }
    public String getApellidos() { return apellidos; }
    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public String getTelefono() { return telefono; }
    public String getCorreo() { return correo; }
    public String getDireccion() { return direccion; }
    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public EstadoPaciente getEstado() { return estado; }
}
