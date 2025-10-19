# Guía de Uso - Sistema de Gestión de Turnos Médicos

## Inicio Rápido

### 1. Compilar el Proyecto

```bash
./compile.sh
```

O manualmente:
```bash
mkdir -p target/classes
find src/main/java -name "*.java" | xargs javac -d target/classes -encoding UTF-8
```

### 2. Ejecutar la Aplicación

```bash
./run.sh
```

O manualmente:
```bash
java -cp target/classes com.turnossalud.Main
```

## Credenciales de Prueba

Al iniciar el sistema, se cargan automáticamente usuarios de prueba:

### Usuario Administrativo
- **Usuario**: `admin`
- **Contraseña**: `admin123`
- **Nombre**: Carlos López

### Usuarios Médicos
| Usuario | Contraseña | Nombre | Especialidad |
|---------|-----------|--------|--------------|
| drperez | medico123 | María Pérez | Clínica General |
| drramirez | medico123 | José Ramírez | Pediatría |
| drgonzalez | medico123 | Ana González | Cardiología |

### Usuarios Pacientes
| Usuario | Contraseña | Nombre |
|---------|-----------|---------|
| jgarcia | paciente123 | Juan García |
| mfernandez | paciente123 | María Fernández |
| lmartinez | paciente123 | Luis Martínez |

## Guía por Rol de Usuario

---

## 🔧 ROL: ADMINISTRATIVO

### Funciones Disponibles
1. Gestión de Personal
2. Gestión de Pacientes
3. Gestión de Turnos
4. Visualizar Disponibilidad

### Caso de Uso 1: Registrar un Médico

1. Iniciar sesión con `admin` / `admin123`
2. Seleccionar opción **1. Gestión de Personal**
3. Seleccionar opción **1. Registrar Médico**
4. Completar el formulario:
   - Nombre de usuario: `drfernandez`
   - Contraseña: `medico123`
   - Nombre: `Roberto`
   - Apellido: `Fernández`
   - DNI: `29123456`
   - Email: `rfernandez@turnossalud.com`
   - Teléfono: `1167890123`
   - Matrícula: `MP-33445`
   - Especialidad: Seleccionar de la lista (ej: `4` para Dermatología)
   - Días de atención: `Lunes,Martes,Miércoles`
   - Horario inicio: `08:00`
   - Horario fin: `14:00`

5. Confirmar el registro

### Caso de Uso 2: Registrar un Paciente

1. Iniciar sesión con `admin` / `admin123`
2. Seleccionar opción **2. Gestión de Pacientes**
3. Seleccionar opción **1. Registrar Paciente**
4. Completar el formulario:
   - Nombre de usuario: `psanchez`
   - Contraseña: `paciente123`
   - Nombre: `Pedro`
   - Apellido: `Sánchez`
   - DNI: `36123456`
   - Email: `psanchez@email.com`
   - Teléfono: `1145678901`
   - Fecha de nacimiento: `15/06/1995`
   - Dirección: `Calle Rivadavia 1234`
   - Obra Social: `OSDE`
   - Número de Afiliado: `456789`

5. Confirmar el registro

### Caso de Uso 3: Visualizar Disponibilidad de Turnos

1. Iniciar sesión con `admin` / `admin123`
2. Seleccionar opción **4. Visualizar Disponibilidad**
3. Seleccionar especialidad (ej: `1` para Clínica General)
4. Seleccionar médico de la lista (ej: `1` para María Pérez)
5. Ingresar fecha: `25/10/2025`
6. Ver lista de horarios disponibles

### Caso de Uso 4: Listar y Cancelar Turnos

1. Iniciar sesión con `admin` / `admin123`
2. Seleccionar opción **3. Gestión de Turnos**
3. Seleccionar opción **1. Listar Todos los Turnos**
4. Ver lista completa de turnos
5. Para cancelar:
   - Volver al menú de Gestión de Turnos
   - Seleccionar opción **2. Cancelar Turno**
   - Ingresar ID del turno a cancelar
   - Ingresar motivo de cancelación

---

## 👨‍⚕️ ROL: MÉDICO

### Funciones Disponibles
1. Ver Mis Turnos
2. Completar Turno
3. Ver Turnos del Día

### Caso de Uso 1: Ver Agenda Completa

1. Iniciar sesión con `drperez` / `medico123`
2. Seleccionar opción **1. Ver Mis Turnos**
3. Ver lista completa de turnos asignados con:
   - ID del turno
   - Nombre del paciente
   - Fecha y hora
   - Estado
   - Motivo de consulta

### Caso de Uso 2: Ver Turnos del Día

1. Iniciar sesión con `drperez` / `medico123`
2. Seleccionar opción **3. Ver Turnos del Día**
3. Ver lista de turnos para el día actual con:
   - Hora de atención
   - Datos del paciente
   - DNI del paciente
   - Estado del turno
   - Motivo de consulta

### Caso de Uso 3: Completar un Turno

1. Iniciar sesión con `drperez` / `medico123`
2. Seleccionar opción **2. Completar Turno**
3. Ingresar ID del turno (verificar en "Ver Mis Turnos")
4. Ingresar observaciones médicas:
   ```
   Paciente presenta cuadro de gripe común.
   Se receta paracetamol 500mg cada 8 horas por 5 días.
   Control en 7 días si no mejora.
   ```
5. Confirmar - El turno cambiará a estado COMPLETADO

---

## 👤 ROL: PACIENTE

### Funciones Disponibles
1. Solicitar Turno
2. Ver Mis Turnos
3. Cancelar Turno
4. Ver Disponibilidad

### Caso de Uso 1: Solicitar un Turno Médico

1. Iniciar sesión con `jgarcia` / `paciente123`
2. Seleccionar opción **1. Solicitar Turno**
3. Seleccionar especialidad (ej: `1` para Clínica General)
4. Seleccionar médico de la lista (ej: `1` para María Pérez)
5. Ingresar fecha del turno: `22/10/2025`
6. Seleccionar horario de la lista disponible (ej: `3` para 10:00)
7. Ingresar motivo de consulta: `Control de rutina y renovación de recetas`
8. Confirmar - Recibirá el ID del turno creado

### Caso de Uso 2: Ver Historial de Turnos

1. Iniciar sesión con `jgarcia` / `paciente123`
2. Seleccionar opción **2. Ver Mis Turnos**
3. Ver lista completa con:
   - ID del turno
   - Médico asignado
   - Especialidad
   - Fecha y hora
   - Estado actual
   - Motivo de consulta
   - Observaciones (si el turno está completado)

### Caso de Uso 3: Cancelar un Turno

1. Iniciar sesión con `jgarcia` / `paciente123`
2. Primero ver turnos activos: Opción **2. Ver Mis Turnos**
3. Anotar el ID del turno a cancelar
4. Volver al menú principal
5. Seleccionar opción **3. Cancelar Turno**
6. Ingresar ID del turno
7. Ingresar motivo: `No podré asistir por viaje de trabajo`
8. Confirmar - El turno cambiará a estado CANCELADO

### Caso de Uso 4: Consultar Disponibilidad

1. Iniciar sesión con `jgarcia` / `paciente123`
2. Seleccionar opción **4. Ver Disponibilidad**
3. Seleccionar especialidad (ej: `2` para Pediatría)
4. Seleccionar médico (ej: `1` para José Ramírez)
5. Ingresar fecha: `23/10/2025`
6. Ver lista de horarios disponibles
7. Tomar nota del horario deseado para solicitar turno

---

## Flujo Completo de Ejemplo

### Escenario: Paciente solicita turno y el médico lo atiende

#### Paso 1: Paciente solicita turno
```
Usuario: jgarcia / paciente123
→ Solicitar Turno
→ Especialidad: Clínica General
→ Médico: María Pérez
→ Fecha: 20/10/2025
→ Horario: 10:00
→ Motivo: Dolor de cabeza persistente
✓ Turno ID: 1 creado
```

#### Paso 2: Administrativo revisa turnos
```
Usuario: admin / admin123
→ Gestión de Turnos
→ Listar Todos los Turnos
→ Ve turno ID: 1 en estado PENDIENTE
```

#### Paso 3: Médico revisa su agenda
```
Usuario: drperez / medico123
→ Ver Mis Turnos
→ Ve turno con Juan García a las 10:00
→ Motivo: Dolor de cabeza persistente
```

#### Paso 4: Día de la consulta - Médico atiende
```
Usuario: drperez / medico123
→ Ver Turnos del Día
→ Ve turno con Juan García
→ Completar Turno
→ ID: 1
→ Observaciones: "Diagnóstico: Cefalea tensional.
   Tratamiento: Ibuprofeno 400mg cada 8h por 3 días.
   Recomendaciones: Descanso adecuado, hidratación.
   Evolución favorable, no requiere estudios adicionales."
✓ Turno completado
```

#### Paso 5: Paciente revisa su historial
```
Usuario: jgarcia / paciente123
→ Ver Mis Turnos
→ Ve turno ID: 1 en estado COMPLETADO
→ Lee observaciones del médico
```

---

## Formatos de Datos

### Fechas
- Formato: `DD/MM/YYYY`
- Ejemplo: `25/10/2025`

### Horarios
- Formato: `HH:mm` (24 horas)
- Ejemplo: `09:00`, `14:30`

### Días de Atención
- Separados por coma
- Ejemplo: `Lunes,Martes,Miércoles`

---

## Validaciones del Sistema

### Al Crear Usuarios
- ✓ Nombre de usuario único
- ✓ DNI único
- ✓ Email en formato válido (básico)
- ✓ Campos obligatorios completos

### Al Crear Turnos
- ✓ Fecha no puede estar en el pasado
- ✓ Médico debe estar disponible en ese horario
- ✓ No se permite doble reserva
- ✓ Horario debe estar dentro del rango del médico

### Al Cancelar Turnos
- ✓ Turno debe existir
- ✓ No se puede cancelar turno ya COMPLETADO
- ✓ No se puede cancelar turno ya CANCELADO
- ✓ Debe proporcionar motivo de cancelación

---

## Mensajes de Error Comunes

### "El nombre de usuario ya existe"
**Causa**: Intento de registrar un usuario con nombre duplicado
**Solución**: Elegir otro nombre de usuario

### "No se pueden crear turnos en el pasado"
**Causa**: Fecha seleccionada es anterior a hoy
**Solución**: Seleccionar una fecha futura

### "El médico ya tiene un turno asignado en ese horario"
**Causa**: Doble reserva
**Solución**: Elegir otro horario disponible

### "Credenciales inválidas"
**Causa**: Usuario o contraseña incorrectos
**Solución**: Verificar las credenciales de prueba

### "Turno no encontrado"
**Causa**: ID de turno incorrecto
**Solución**: Verificar el ID en la lista de turnos

---

## Tips y Recomendaciones

### Para Administrativos
1. Registrar médicos con horarios variados para mejor cobertura
2. Revisar regularmente la disponibilidad de turnos
3. Cancelar turnos con motivos claros para auditoría

### Para Médicos
1. Revisar "Ver Turnos del Día" al iniciar la jornada
2. Completar turnos con observaciones detalladas
3. Mantener actualizada la agenda

### Para Pacientes
1. Solicitar turnos con anticipación
2. Cancelar con tiempo si no puede asistir
3. Verificar disponibilidad antes de solicitar
4. Llevar registro del ID del turno

---

## Solución de Problemas

### El sistema no compila
```bash
# Verificar versión de Java
java -version  # Debe ser 11 o superior

# Limpiar y recompilar
rm -rf target/classes
./compile.sh
```

### Error al ejecutar
```bash
# Verificar que esté compilado
ls -la target/classes/com/turnossalud/

# Si no existe, compilar primero
./compile.sh
./run.sh
```

### No aparecen los usuarios de prueba
**Causa**: Los datos se cargan en memoria al iniciar
**Solución**: Reiniciar la aplicación

---

## Características Avanzadas

### Configuración de Médicos
- Duración de turno: 30 minutos por defecto
- Horarios personalizables por médico
- Días de atención flexibles

### Estados de Turno
- **PENDIENTE**: Recién creado
- **CONFIRMADO**: Confirmado por administrativo o paciente
- **COMPLETADO**: Atención finalizada con observaciones
- **CANCELADO**: No se realizará, con motivo registrado

### Especialidades Disponibles
1. Clínica General
2. Pediatría
3. Cardiología
4. Dermatología
5. Traumatología
6. Ginecología
7. Oftalmología
8. Odontología

---

## Preguntas Frecuentes

**P: ¿Puedo cambiar mi contraseña?**
R: Actualmente no desde la interfaz. Es una mejora futura.

**P: ¿Los datos se guardan al cerrar?**
R: No, el sistema usa memoria. Los datos se pierden al cerrar. Para persistencia, se requiere migración a BD.

**P: ¿Puedo tener múltiples sesiones?**
R: No, el sistema maneja una sesión a la vez.

**P: ¿Cómo agrego más especialidades?**
R: Se debe modificar el enum `Especialidad.java` y recompilar.

**P: ¿El sistema es multi-idioma?**
R: No, actualmente solo español.

---

## Contacto y Soporte

Para reportar problemas o sugerencias:
- Revisar documentación en `README.md`
- Consultar arquitectura en `ARQUITECTURA.md`
- Verificar código fuente en `src/main/java/`

---

**Sistema de Gestión de Turnos Médicos v1.0**
*Desarrollado con ❤️ para centros de salud comunitarios*
