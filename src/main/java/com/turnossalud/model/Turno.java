package com.turnossalud.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Clase que representa un turno médico.
 */
public class Turno {
    private Long id;
    private Paciente paciente;
    private Medico medico;
    private LocalDateTime fechaHora;
    private EstadoTurno estado;
    private String motivoConsulta;
    private String observaciones;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaCancelacion;
    private String motivoCancelacion;

    public Turno() {
        this.estado = EstadoTurno.PENDIENTE;
        this.fechaCreacion = LocalDateTime.now();
    }

    public Turno(Paciente paciente, Medico medico, LocalDateTime fechaHora) {
        this();
        this.paciente = paciente;
        this.medico = medico;
        this.fechaHora = fechaHora;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public EstadoTurno getEstado() {
        return estado;
    }

    public void setEstado(EstadoTurno estado) {
        this.estado = estado;
    }

    public String getMotivoConsulta() {
        return motivoConsulta;
    }

    public void setMotivoConsulta(String motivoConsulta) {
        this.motivoConsulta = motivoConsulta;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDateTime getFechaCancelacion() {
        return fechaCancelacion;
    }

    public void setFechaCancelacion(LocalDateTime fechaCancelacion) {
        this.fechaCancelacion = fechaCancelacion;
    }

    public String getMotivoCancelacion() {
        return motivoCancelacion;
    }

    public void setMotivoCancelacion(String motivoCancelacion) {
        this.motivoCancelacion = motivoCancelacion;
    }

    public void cancelar(String motivo) {
        this.estado = EstadoTurno.CANCELADO;
        this.fechaCancelacion = LocalDateTime.now();
        this.motivoCancelacion = motivo;
    }

    public void confirmar() {
        this.estado = EstadoTurno.CONFIRMADO;
    }

    public void completar() {
        this.estado = EstadoTurno.COMPLETADO;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return "Turno " + id + " - " + paciente.getUsuario().getNombreCompleto() + 
               " con " + medico.getUsuario().getNombreCompleto() + 
               " el " + fechaHora.format(formatter) + " - " + estado.getNombre();
    }
}
