# Arquitectura del Sistema de Gestión de Turnos Médicos

## Introducción

Este documento describe la arquitectura de software del Sistema de Gestión de Turnos Médicos, diseñado siguiendo el patrón de **Arquitectura en Tres Capas** (Three-Tier Architecture).

## Arquitectura en Tres Capas

### 1. Capa de Presentación (Presentation Layer)

**Responsabilidad**: Interfaz con el usuario y gestión de interacciones.

**Ubicación**: `com.turnossalud.ui`

**Componentes**:
- `MenuPrincipal.java`: Menú de inicio de sesión y navegación principal
- `MenuAdministrativo.java`: Interfaz para usuarios administrativos
- `MenuMedico.java`: Interfaz para médicos
- `MenuPaciente.java`: Interfaz para pacientes

**Características**:
- Implementación basada en consola (CLI)
- Validación de entrada de usuario
- Formateo de salida con caracteres Unicode
- Navegación por menús jerárquicos
- Sin lógica de negocio (delegada a la capa de servicio)

### 2. Capa de Lógica de Negocio (Business Logic Layer)

**Responsabilidad**: Implementar las reglas del negocio y coordinar operaciones.

**Ubicación**: `com.turnossalud.service`

**Componentes**:
- `AutenticacionService.java`: Gestión de autenticación y sesiones
- `UsuarioService.java`: Gestión de usuarios, médicos y pacientes
- `TurnoService.java`: Gestión de turnos y disponibilidad

**Reglas de Negocio Implementadas**:

#### AutenticacionService
- Validación de credenciales
- Gestión de sesión activa
- Control de acceso basado en roles
- Verificación de usuarios activos

#### UsuarioService
- Validación de unicidad de nombre de usuario
- Validación de DNI único
- Registro de usuarios con roles específicos
- Asignación de especialidades a médicos
- Gestión de horarios de atención
- Activación/desactivación de usuarios

#### TurnoService
- Validación de fechas (no permitir turnos en el pasado)
- Prevención de doble reserva (un médico, un horario)
- Generación automática de slots horarios
- Gestión de estados de turno
- Validación de cancelaciones (no cancelar turnos completados)
- Cálculo de disponibilidad en tiempo real

### 3. Capa de Acceso a Datos (Data Access Layer)

**Responsabilidad**: Persistencia y recuperación de datos.

**Ubicación**: `com.turnossalud.dao`

**Componentes**:

#### Interfaces DAO
- `UsuarioDAO.java`
- `MedicoDAO.java`
- `PacienteDAO.java`
- `TurnoDAO.java`

#### Implementaciones
- `UsuarioDAOImpl.java`
- `MedicoDAOImpl.java`
- `PacienteDAOImpl.java`
- `TurnoDAOImpl.java`

**Características**:
- Implementación en memoria usando `ConcurrentHashMap`
- Thread-safe para operaciones concurrentes
- Generación automática de IDs con `AtomicLong`
- Operaciones CRUD completas
- Búsquedas y filtros especializados
- Independencia de la tecnología de persistencia (fácil migración a BD)

## Modelo de Dominio

**Ubicación**: `com.turnossalud.model`

### Entidades Principales

#### Usuario
- Información base de todos los usuarios
- Campos: id, nombreUsuario, contrasena, nombre, apellido, dni, email, telefono, rol, fechaRegistro, activo
- Relación: 1:1 con Medico o Paciente según rol

#### Medico
- Información profesional
- Campos: id, usuario, matricula, especialidad, diasAtencion, horarioInicio, horarioFin, duracionTurnoMinutos, activo
- Relación: 1:N con Turno

#### Paciente
- Información médica del paciente
- Campos: id, usuario, fechaNacimiento, direccion, obraSocial, numeroAfiliado, grupoSanguineo, alergias, activo
- Relación: 1:N con Turno

#### Turno
- Registro de cita médica
- Campos: id, paciente, medico, fechaHora, estado, motivoConsulta, observaciones, fechaCreacion, fechaCancelacion, motivoCancelacion
- Estados: PENDIENTE, CONFIRMADO, COMPLETADO, CANCELADO

### Enumeraciones

#### Rol
- ADMINISTRATIVO: Gestión del sistema
- MEDICO: Atención médica
- PACIENTE: Usuario del servicio

#### Especialidad
- CLINICA_GENERAL
- PEDIATRIA
- CARDIOLOGIA
- DERMATOLOGIA
- TRAUMATOLOGIA
- GINECOLOGIA
- OFTALMOLOGIA
- ODONTOLOGIA

#### EstadoTurno
- PENDIENTE: Turno creado, esperando
- CONFIRMADO: Turno confirmado
- COMPLETADO: Atención finalizada
- CANCELADO: Turno cancelado

## Flujo de Datos

### Ejemplo: Solicitar un Turno

```
1. Usuario (Paciente) → MenuPaciente
   ↓
2. MenuPaciente → TurnoService.crearTurno()
   ↓
3. TurnoService → Validaciones:
   - Fecha no en el pasado
   - Médico disponible en ese horario
   ↓
4. TurnoService → TurnoDAO.guardar()
   ↓
5. TurnoDAO → ConcurrentHashMap (almacenamiento)
   ↓
6. Turno creado ← retorna por todas las capas
   ↓
7. MenuPaciente ← muestra confirmación al usuario
```

## Patrones de Diseño Utilizados

### 1. Data Access Object (DAO)
- Abstracción de operaciones de persistencia
- Interfaces DAO definen contratos
- Implementaciones pueden cambiar sin afectar otras capas

### 2. Service Layer
- Capa intermedia que encapsula lógica de negocio
- Coordina múltiples DAOs si es necesario
- Transacciones y validaciones centralizadas

### 3. Dependency Injection
- Inyección manual en `Main.java`
- Servicios reciben DAOs como dependencias
- Menús reciben servicios como dependencias
- Facilita testing y mantenimiento

### 4. Singleton (implícito)
- Una sola instancia de cada servicio
- Una sola instancia de cada DAO
- Gestión de estado centralizada

## Principios SOLID Aplicados

### S - Single Responsibility Principle
- Cada clase tiene una única responsabilidad
- DAOs: solo persistencia
- Services: solo lógica de negocio
- UI: solo interacción con usuario

### O - Open/Closed Principle
- Interfaces DAO abiertas para extensión
- Cerradas para modificación
- Fácil agregar nuevas implementaciones (ej: con BD)

### L - Liskov Substitution Principle
- Implementaciones DAO son intercambiables
- Cualquier implementación de UsuarioDAO funciona igual

### I - Interface Segregation Principle
- Interfaces DAO específicas por entidad
- No interfaces grandes con métodos innecesarios

### D - Dependency Inversion Principle
- Capas superiores dependen de abstracciones (interfaces)
- No de implementaciones concretas
- Services usan interfaces DAO, no implementaciones

## Seguridad

### Autenticación
- Validación de credenciales en AutenticacionService
- Contraseñas almacenadas en texto plano (para demo; usar hash en producción)
- Sesión de usuario activa gestionada

### Autorización
- Control de acceso basado en roles (RBAC)
- Cada menú verifica el rol del usuario
- Operaciones restringidas según permisos

### Validaciones
- Nivel de UI: formato de entrada
- Nivel de Service: reglas de negocio
- Nivel de DAO: integridad de datos

## Concurrencia

### Thread Safety
- `ConcurrentHashMap` en todos los DAOs
- `AtomicLong` para generación de IDs
- Operaciones atómicas garantizadas

### Consideraciones
- Sistema diseñado para uso single-thread (CLI)
- Preparado para multi-thread si se migra a web/GUI

## Escalabilidad

### Vertical
- Fácil agregar nuevas especialidades (enum)
- Fácil agregar nuevos roles (enum)
- Fácil agregar campos a entidades

### Horizontal
- Arquitectura preparada para migrar a:
  - Base de datos relacional (MySQL, PostgreSQL)
  - API REST (Spring Boot)
  - Interfaz web (React, Angular)
  - Aplicación desktop (JavaFX)

## Mantenibilidad

### Separación de Concerns
- Cada capa tiene responsabilidades claras
- Cambios en UI no afectan lógica
- Cambios en persistencia no afectan lógica

### Testabilidad
- Interfaces facilitan mocking
- Lógica de negocio aislada
- Fácil crear tests unitarios

### Documentación
- Javadoc en clases y métodos clave
- Comentarios descriptivos
- README completo con ejemplos

## Diagrama de Arquitectura

```
┌─────────────────────────────────────────────────┐
│          CAPA DE PRESENTACIÓN (UI)              │
│  ┌──────────────┐  ┌──────────┐  ┌───────────┐ │
│  │MenuPrincipal │  │MenuAdmin │  │MenuMedico │ │
│  │              │  │          │  │           │ │
│  └──────┬───────┘  └────┬─────┘  └─────┬─────┘ │
└─────────┼────────────────┼──────────────┼───────┘
          │                │              │
          ▼                ▼              ▼
┌─────────────────────────────────────────────────┐
│      CAPA DE LÓGICA DE NEGOCIO (SERVICE)        │
│  ┌────────────────┐  ┌──────────────────────┐  │
│  │Autenticacion   │  │UsuarioService        │  │
│  │Service         │  │                      │  │
│  └────────────────┘  └──────────────────────┘  │
│  ┌────────────────────────────────────────────┐ │
│  │TurnoService                                │ │
│  │                                            │ │
│  └────────────┬───────────────────────────────┘ │
└───────────────┼─────────────────────────────────┘
                │
                ▼
┌─────────────────────────────────────────────────┐
│      CAPA DE ACCESO A DATOS (DAO)               │
│  ┌────────────┐  ┌──────────┐  ┌────────────┐  │
│  │UsuarioDAO  │  │MedicoDAO │  │PacienteDAO │  │
│  │(Impl)      │  │(Impl)    │  │(Impl)      │  │
│  └────────────┘  └──────────┘  └────────────┘  │
│  ┌────────────┐                                 │
│  │TurnoDAO    │                                 │
│  │(Impl)      │                                 │
│  └──────┬─────┘                                 │
└─────────┼───────────────────────────────────────┘
          │
          ▼
┌─────────────────────────────────────────────────┐
│         ALMACENAMIENTO (In-Memory)              │
│         ConcurrentHashMap                       │
└─────────────────────────────────────────────────┘
```

## Métricas del Proyecto

- **Total de archivos Java**: 23
- **Total de líneas de código**: ~2,428
- **Capas implementadas**: 3
- **Entidades del dominio**: 4 principales + 3 enums
- **Servicios**: 3
- **DAOs**: 4 (interfaces + implementaciones)
- **Componentes UI**: 4

## Posibles Mejoras Futuras

### Corto Plazo
1. Agregar tests unitarios con JUnit
2. Implementar logging con SLF4J
3. Agregar validación de email y teléfono
4. Mejorar manejo de excepciones

### Mediano Plazo
1. Migrar a base de datos (JPA/Hibernate)
2. Implementar interfaz gráfica (JavaFX)
3. Agregar reportes y estadísticas
4. Notificaciones por email/SMS
5. Historial médico del paciente

### Largo Plazo
1. API REST con Spring Boot
2. Aplicación web con framework moderno
3. Aplicación móvil (Android/iOS)
4. Integración con sistemas de facturación
5. Sistema de recordatorios automáticos
6. Dashboard de analíticas

## Conclusión

Este sistema implementa una arquitectura sólida, escalable y mantenible que cumple con los requerimientos establecidos. La separación en tres capas permite:

- **Mantenibilidad**: Cambios localizados en cada capa
- **Testabilidad**: Fácil crear tests para cada componente
- **Escalabilidad**: Preparado para crecer en funcionalidad y usuarios
- **Flexibilidad**: Fácil cambiar implementaciones (ej: BD)
- **Claridad**: Código organizado y fácil de entender

La arquitectura elegida es apropiada para un sistema de gestión de turnos médicos y proporciona una base sólida para futuras expansiones.
