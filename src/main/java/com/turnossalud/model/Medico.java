package com.turnossalud.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa un médico en el sistema.
 */
public class Medico {
    private Long id;
    private Usuario usuario;
    private String matricula;
    private Especialidad especialidad;
    private List<String> diasAtencion;
    private String horarioInicio;
    private String horarioFin;
    private int duracionTurnoMinutos;
    private boolean activo;

    public Medico() {
        this.diasAtencion = new ArrayList<>();
        this.duracionTurnoMinutos = 30;
        this.activo = true;
    }

    public Medico(Usuario usuario, String matricula, Especialidad especialidad) {
        this();
        this.usuario = usuario;
        this.matricula = matricula;
        this.especialidad = especialidad;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public Especialidad getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(Especialidad especialidad) {
        this.especialidad = especialidad;
    }

    public List<String> getDiasAtencion() {
        return diasAtencion;
    }

    public void setDiasAtencion(List<String> diasAtencion) {
        this.diasAtencion = diasAtencion;
    }

    public String getHorarioInicio() {
        return horarioInicio;
    }

    public void setHorarioInicio(String horarioInicio) {
        this.horarioInicio = horarioInicio;
    }

    public String getHorarioFin() {
        return horarioFin;
    }

    public void setHorarioFin(String horarioFin) {
        this.horarioFin = horarioFin;
    }

    public int getDuracionTurnoMinutos() {
        return duracionTurnoMinutos;
    }

    public void setDuracionTurnoMinutos(int duracionTurnoMinutos) {
        this.duracionTurnoMinutos = duracionTurnoMinutos;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    @Override
    public String toString() {
        return usuario.getNombreCompleto() + " - " + especialidad.getNombre() + 
               " (Mat: " + matricula + ")";
    }
}
