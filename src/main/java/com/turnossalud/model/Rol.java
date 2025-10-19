package com.turnossalud.model;

/**
 * Enumeración que define los roles de usuario en el sistema.
 */
public enum Rol {
    ADMINISTRATIVO("Administrativo"),
    MEDICO("Médico"),
    PACIENTE("Paciente");

    private final String nombre;

    Rol(String nombre) {
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
