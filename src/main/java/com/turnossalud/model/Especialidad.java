package com.turnossalud.model;

/**
 * Enumeración que define las especialidades médicas disponibles.
 */
public enum Especialidad {
    CLINICA_GENERAL("Clínica General"),
    PEDIATRIA("Pediatría"),
    CARDIOLOGIA("Cardiología"),
    DERMATOLOGIA("Dermatología"),
    TRAUMATOLOGIA("Traumatología"),
    GINECOLOGIA("Ginecología"),
    OFTALMOLOGIA("Oftalmología"),
    ODONTOLOGIA("Odontología");

    private final String nombre;

    Especialidad(String nombre) {
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
