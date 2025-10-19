package com.turnossalud.model;

/**
 * Enumeración que define los estados posibles de un turno.
 */
public enum EstadoTurno {
    PENDIENTE("Pendiente"),
    CONFIRMADO("Confirmado"),
    CANCELADO("Cancelado"),
    COMPLETADO("Completado");

    private final String nombre;

    EstadoTurno(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
