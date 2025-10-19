#!/bin/bash
# Script para ejecutar la aplicación

echo "Ejecutando Sistema de Gestión de Turnos Médicos..."

# Compilar si no está compilado
if [ ! -d "target/classes" ]; then
    ./compile.sh
fi

# Ejecutar la aplicación
java -cp target/classes com.turnossalud.Main
