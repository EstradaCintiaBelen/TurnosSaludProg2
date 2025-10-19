#!/bin/bash
# Script para compilar la aplicación

echo "Compilando Sistema de Gestión de Turnos Médicos..."

# Crear directorio de salida si no existe
mkdir -p target/classes

# Compilar todos los archivos Java
find src/main/java -name "*.java" | xargs javac -d target/classes -encoding UTF-8

if [ $? -eq 0 ]; then
    echo "✓ Compilación exitosa"
    echo "Los archivos compilados están en: target/classes"
else
    echo "✗ Error en la compilación"
    exit 1
fi
