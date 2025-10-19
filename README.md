# Sistema de Gestión de Turnos Médicos

Sistema de escritorio desarrollado en Java para gestionar turnos médicos en centros de salud comunitarios.

## 📋 Descripción

Aplicación de escritorio que implementa un sistema completo de gestión de turnos médicos con arquitectura de software en tres capas:

- **Capa de Presentación**: Interfaz de usuario basada en consola
- **Capa de Lógica de Negocio**: Servicios que implementan las reglas del negocio
- **Capa de Acceso a Datos**: Gestión de persistencia mediante patrón DAO (implementación en memoria)

## 🏗️ Arquitectura

```
com.turnossalud
├── model/           # Modelos de dominio (Entidades)
│   ├── Usuario.java
│   ├── Medico.java
│   ├── Paciente.java
│   ├── Turno.java
│   ├── Rol.java
│   ├── Especialidad.java
│   └── EstadoTurno.java
├── dao/             # Capa de Acceso a Datos
│   ├── UsuarioDAO.java
│   ├── MedicoDAO.java
│   ├── PacienteDAO.java
│   ├── TurnoDAO.java
│   └── Implementaciones (DAOImpl)
├── service/         # Capa de Lógica de Negocio
│   ├── AutenticacionService.java
│   ├── UsuarioService.java
│   └── TurnoService.java
└── ui/              # Capa de Presentación
    ├── MenuPrincipal.java
    ├── MenuAdministrativo.java
    ├── MenuMedico.java
    └── MenuPaciente.java
```

## ✨ Funcionalidades

### 🔐 Sistema de Autenticación
- Inicio de sesión con usuario y contraseña
- Control de acceso basado en roles
- Sesiones de usuario

### 👥 Gestión de Personal y Usuarios

#### Rol: Administrativo
- Registrar médicos con especialidades
- Registrar personal administrativo
- Registrar pacientes
- Asignar horarios de atención a médicos
- Gestionar turnos (listar, cancelar)
- Visualizar disponibilidad de turnos por especialidad y profesional

#### Rol: Médico
- Ver agenda de turnos asignados
- Ver turnos del día actual
- Completar turnos con observaciones
- Consultar información de pacientes

#### Rol: Paciente
- Solicitar turnos médicos
- Ver turnos propios (historial)
- Cancelar turnos
- Consultar disponibilidad por especialidad y médico

### 📅 Gestión de Turnos
- Creación de turnos con validaciones
- Verificación de disponibilidad horaria
- Estados de turno: Pendiente, Confirmado, Completado, Cancelado
- Asignación automática de horarios según configuración del médico
- Visualización de disponibilidad en tiempo real

### 🏥 Especialidades Médicas
- Clínica General
- Pediatría
- Cardiología
- Dermatología
- Traumatología
- Ginecología
- Oftalmología
- Odontología

## 🚀 Instalación y Ejecución

### Requisitos
- Java JDK 11 o superior
- Sistema operativo: Linux, macOS o Windows

### Compilación

#### En Linux/macOS:
```bash
./compile.sh
```

#### En Windows o manualmente:
```bash
mkdir -p target/classes
find src/main/java -name "*.java" | xargs javac -d target/classes -encoding UTF-8
```

### Ejecución

#### En Linux/macOS:
```bash
./run.sh
```

#### En Windows o manualmente:
```bash
java -cp target/classes com.turnossalud.Main
```

## 👤 Usuarios de Prueba

El sistema se inicializa con los siguientes usuarios de prueba:

### Administrativo
- **Usuario**: `admin`
- **Contraseña**: `admin123`
- **Funciones**: Gestión completa del sistema

### Médicos
| Usuario | Contraseña | Especialidad | Horario |
|---------|-----------|--------------|---------|
| drperez | medico123 | Clínica General | 09:00 - 17:00 |
| drramirez | medico123 | Pediatría | 10:00 - 16:00 |
| drgonzalez | medico123 | Cardiología | 08:00 - 14:00 |

### Pacientes
| Usuario | Contraseña | Nombre |
|---------|-----------|---------|
| jgarcia | paciente123 | Juan García |
| mfernandez | paciente123 | María Fernández |
| lmartinez | paciente123 | Luis Martínez |

## 📖 Guía de Uso

### Para Administrativos

1. **Iniciar sesión** con usuario `admin`
2. **Registrar personal**:
   - Seleccionar "Gestión de Personal"
   - Elegir tipo de personal (Médico/Administrativo)
   - Completar formulario de registro
3. **Registrar pacientes**:
   - Seleccionar "Gestión de Pacientes"
   - Completar datos personales y de obra social
4. **Gestionar turnos**:
   - Listar todos los turnos
   - Cancelar turnos si es necesario
5. **Visualizar disponibilidad**:
   - Seleccionar especialidad y médico
   - Consultar horarios disponibles

### Para Médicos

1. **Iniciar sesión** con credenciales de médico
2. **Ver turnos**:
   - "Ver Mis Turnos": Lista completa de turnos
   - "Ver Turnos del Día": Agenda del día actual
3. **Completar turno**:
   - Ingresar ID del turno
   - Agregar observaciones de la consulta

### Para Pacientes

1. **Iniciar sesión** con credenciales de paciente
2. **Solicitar turno**:
   - Seleccionar especialidad
   - Elegir médico
   - Seleccionar fecha y horario disponible
   - Indicar motivo de consulta
3. **Ver turnos propios**:
   - Consultar historial completo
   - Ver estado de cada turno
4. **Cancelar turno**:
   - Ingresar ID del turno
   - Indicar motivo de cancelación

## 🔒 Seguridad

- Autenticación obligatoria para acceder al sistema
- Control de acceso basado en roles (RBAC)
- Validación de permisos en cada operación
- Usuarios activos/inactivos

## 🛠️ Tecnologías

- **Lenguaje**: Java 11+
- **Arquitectura**: Tres capas (Presentation, Business Logic, Data Access)
- **Patrón de diseño**: DAO (Data Access Object)
- **Almacenamiento**: En memoria (ConcurrentHashMap para thread-safety)
- **Interfaz**: Consola (CLI)

## 📊 Modelo de Datos

### Entidades Principales

- **Usuario**: Información base de todos los usuarios del sistema
- **Médico**: Información profesional y horarios de atención
- **Paciente**: Información médica y de obra social
- **Turno**: Registro de citas médicas con estado y observaciones

### Relaciones

- Usuario 1:1 Médico (usuario con rol MEDICO)
- Usuario 1:1 Paciente (usuario con rol PACIENTE)
- Médico 1:N Turno
- Paciente 1:N Turno

## 🔄 Estados de Turno

1. **PENDIENTE**: Turno creado, esperando confirmación
2. **CONFIRMADO**: Turno confirmado por el paciente/administrativo
3. **COMPLETADO**: Turno realizado, con observaciones médicas
4. **CANCELADO**: Turno cancelado con motivo registrado

## 📝 Validaciones

- No se pueden crear turnos en el pasado
- No se pueden asignar dos turnos al mismo médico en el mismo horario
- Validación de formatos de fecha y hora
- Verificación de existencia de usuario/DNI duplicado
- Control de estados de turno (no cancelar turnos completados, etc.)

## 🎯 Próximas Mejoras

- Persistencia en base de datos (MySQL/PostgreSQL)
- Interfaz gráfica (JavaFX o Swing)
- Notificaciones por email/SMS
- Reportes y estadísticas
- Historial médico del paciente
- Integración con sistemas de facturación

## 👨‍💻 Desarrollo

Este proyecto fue desarrollado como parte del curso de Programación 2, implementando conceptos de:
- Programación Orientada a Objetos
- Arquitectura de Software en Capas
- Patrones de Diseño (DAO, Service Layer)
- Manejo de colecciones y streams
- Enumeraciones y tipos de datos
- Validaciones de negocio

## 📄 Licencia

Este proyecto es de código abierto y está disponible para fines educativos.

---

**Desarrollado con ❤️ para la gestión eficiente de turnos médicos en centros de salud comunitarios**
