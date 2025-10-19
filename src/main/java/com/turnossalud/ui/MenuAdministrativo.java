package com.turnossalud.ui;

import com.turnossalud.model.*;
import com.turnossalud.service.AutenticacionService;
import com.turnossalud.service.UsuarioService;
import com.turnossalud.service.TurnoService;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Menú para usuarios con rol Administrativo.
 */
public class MenuAdministrativo {
    private final Scanner scanner;
    private final AutenticacionService autenticacionService;
    private final UsuarioService usuarioService;
    private final TurnoService turnoService;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public MenuAdministrativo(AutenticacionService autenticacionService,
                             UsuarioService usuarioService,
                             TurnoService turnoService) {
        this.scanner = new Scanner(System.in);
        this.autenticacionService = autenticacionService;
        this.usuarioService = usuarioService;
        this.turnoService = turnoService;
    }

    public void mostrar() {
        boolean continuar = true;

        while (continuar && autenticacionService.estaAutenticado()) {
            System.out.println("\n╔════════════════════════════════════════════════╗");
            System.out.println("║         MENÚ ADMINISTRATIVO                    ║");
            System.out.println("╚════════════════════════════════════════════════╝");
            System.out.println("\n1. Gestión de Personal");
            System.out.println("2. Gestión de Pacientes");
            System.out.println("3. Gestión de Turnos");
            System.out.println("4. Visualizar Disponibilidad");
            System.out.println("5. Cerrar Sesión");
            System.out.print("\nSeleccione una opción: ");

            try {
                int opcion = Integer.parseInt(scanner.nextLine().trim());

                switch (opcion) {
                    case 1:
                        menuGestionPersonal();
                        break;
                    case 2:
                        menuGestionPacientes();
                        break;
                    case 3:
                        menuGestionTurnos();
                        break;
                    case 4:
                        visualizarDisponibilidad();
                        break;
                    case 5:
                        autenticacionService.cerrarSesion();
                        continuar = false;
                        System.out.println("\nSesión cerrada exitosamente.");
                        break;
                    default:
                        System.out.println("\nOpción inválida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("\nDebe ingresar un número válido.");
            } catch (Exception e) {
                System.out.println("\nError: " + e.getMessage());
            }
        }
    }

    private void menuGestionPersonal() {
        System.out.println("\n--- GESTIÓN DE PERSONAL ---");
        System.out.println("1. Registrar Médico");
        System.out.println("2. Listar Médicos");
        System.out.println("3. Registrar Administrativo");
        System.out.println("4. Volver");
        System.out.print("\nSeleccione una opción: ");

        try {
            int opcion = Integer.parseInt(scanner.nextLine().trim());

            switch (opcion) {
                case 1:
                    registrarMedico();
                    break;
                case 2:
                    listarMedicos();
                    break;
                case 3:
                    registrarAdministrativo();
                    break;
                case 4:
                    break;
                default:
                    System.out.println("\nOpción inválida.");
            }
        } catch (NumberFormatException e) {
            System.out.println("\nDebe ingresar un número válido.");
        }
    }

    private void registrarMedico() {
        System.out.println("\n--- REGISTRO DE MÉDICO ---");
        
        System.out.print("Nombre de usuario: ");
        String nombreUsuario = scanner.nextLine().trim();
        
        System.out.print("Contraseña: ");
        String contrasena = scanner.nextLine().trim();
        
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine().trim();
        
        System.out.print("Apellido: ");
        String apellido = scanner.nextLine().trim();
        
        System.out.print("DNI: ");
        String dni = scanner.nextLine().trim();
        
        System.out.print("Email: ");
        String email = scanner.nextLine().trim();
        
        System.out.print("Teléfono: ");
        String telefono = scanner.nextLine().trim();
        
        System.out.print("Matrícula: ");
        String matricula = scanner.nextLine().trim();
        
        System.out.println("\nEspecialidades disponibles:");
        int i = 1;
        for (Especialidad esp : Especialidad.values()) {
            System.out.println(i++ + ". " + esp.getNombre());
        }
        System.out.print("Seleccione especialidad: ");
        int espIndex = Integer.parseInt(scanner.nextLine().trim()) - 1;
        Especialidad especialidad = Especialidad.values()[espIndex];
        
        System.out.print("Días de atención (separados por coma, ej: Lunes,Martes,Miércoles): ");
        String diasStr = scanner.nextLine().trim();
        List<String> diasAtencion = Arrays.asList(diasStr.split(","));
        
        System.out.print("Horario de inicio (formato HH:mm): ");
        String horarioInicio = scanner.nextLine().trim();
        
        System.out.print("Horario de fin (formato HH:mm): ");
        String horarioFin = scanner.nextLine().trim();

        Usuario usuario = usuarioService.registrarUsuario(nombreUsuario, contrasena, nombre, apellido, 
                                                          dni, email, telefono, Rol.MEDICO);
        Medico medico = usuarioService.registrarMedico(usuario, matricula, especialidad, 
                                                        diasAtencion, horarioInicio, horarioFin);

        System.out.println("\n¡Médico registrado exitosamente!");
        System.out.println(medico);
    }

    private void registrarAdministrativo() {
        System.out.println("\n--- REGISTRO DE ADMINISTRATIVO ---");
        
        System.out.print("Nombre de usuario: ");
        String nombreUsuario = scanner.nextLine().trim();
        
        System.out.print("Contraseña: ");
        String contrasena = scanner.nextLine().trim();
        
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine().trim();
        
        System.out.print("Apellido: ");
        String apellido = scanner.nextLine().trim();
        
        System.out.print("DNI: ");
        String dni = scanner.nextLine().trim();
        
        System.out.print("Email: ");
        String email = scanner.nextLine().trim();
        
        System.out.print("Teléfono: ");
        String telefono = scanner.nextLine().trim();

        Usuario usuario = usuarioService.registrarUsuario(nombreUsuario, contrasena, nombre, apellido, 
                                                          dni, email, telefono, Rol.ADMINISTRATIVO);

        System.out.println("\n¡Administrativo registrado exitosamente!");
        System.out.println(usuario);
    }

    private void listarMedicos() {
        System.out.println("\n--- LISTA DE MÉDICOS ---");
        List<Medico> medicos = usuarioService.obtenerTodosMedicos();

        if (medicos.isEmpty()) {
            System.out.println("No hay médicos registrados.");
        } else {
            for (Medico medico : medicos) {
                System.out.println("\nID: " + medico.getId());
                System.out.println("Nombre: " + medico.getUsuario().getNombreCompleto());
                System.out.println("Especialidad: " + medico.getEspecialidad().getNombre());
                System.out.println("Matrícula: " + medico.getMatricula());
                System.out.println("Horario: " + medico.getHorarioInicio() + " - " + medico.getHorarioFin());
                System.out.println("Días: " + String.join(", ", medico.getDiasAtencion()));
                System.out.println("Estado: " + (medico.isActivo() ? "Activo" : "Inactivo"));
            }
        }
    }

    private void menuGestionPacientes() {
        System.out.println("\n--- GESTIÓN DE PACIENTES ---");
        System.out.println("1. Registrar Paciente");
        System.out.println("2. Listar Pacientes");
        System.out.println("3. Volver");
        System.out.print("\nSeleccione una opción: ");

        try {
            int opcion = Integer.parseInt(scanner.nextLine().trim());

            switch (opcion) {
                case 1:
                    registrarPaciente();
                    break;
                case 2:
                    listarPacientes();
                    break;
                case 3:
                    break;
                default:
                    System.out.println("\nOpción inválida.");
            }
        } catch (NumberFormatException e) {
            System.out.println("\nDebe ingresar un número válido.");
        }
    }

    private void registrarPaciente() {
        System.out.println("\n--- REGISTRO DE PACIENTE ---");
        
        System.out.print("Nombre de usuario: ");
        String nombreUsuario = scanner.nextLine().trim();
        
        System.out.print("Contraseña: ");
        String contrasena = scanner.nextLine().trim();
        
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine().trim();
        
        System.out.print("Apellido: ");
        String apellido = scanner.nextLine().trim();
        
        System.out.print("DNI: ");
        String dni = scanner.nextLine().trim();
        
        System.out.print("Email: ");
        String email = scanner.nextLine().trim();
        
        System.out.print("Teléfono: ");
        String telefono = scanner.nextLine().trim();
        
        System.out.print("Fecha de nacimiento (dd/MM/yyyy): ");
        String fechaStr = scanner.nextLine().trim();
        LocalDate fechaNacimiento = LocalDate.parse(fechaStr, dateFormatter);
        
        System.out.print("Dirección: ");
        String direccion = scanner.nextLine().trim();
        
        System.out.print("Obra Social: ");
        String obraSocial = scanner.nextLine().trim();
        
        System.out.print("Número de Afiliado: ");
        String numeroAfiliado = scanner.nextLine().trim();

        Usuario usuario = usuarioService.registrarUsuario(nombreUsuario, contrasena, nombre, apellido, 
                                                          dni, email, telefono, Rol.PACIENTE);
        Paciente paciente = usuarioService.registrarPaciente(usuario, fechaNacimiento, 
                                                             direccion, obraSocial, numeroAfiliado);

        System.out.println("\n¡Paciente registrado exitosamente!");
        System.out.println(paciente);
    }

    private void listarPacientes() {
        System.out.println("\n--- LISTA DE PACIENTES ---");
        List<Paciente> pacientes = usuarioService.obtenerTodosPacientes();

        if (pacientes.isEmpty()) {
            System.out.println("No hay pacientes registrados.");
        } else {
            for (Paciente paciente : pacientes) {
                System.out.println("\nID: " + paciente.getId());
                System.out.println("Nombre: " + paciente.getUsuario().getNombreCompleto());
                System.out.println("DNI: " + paciente.getUsuario().getDni());
                System.out.println("Fecha Nacimiento: " + paciente.getFechaNacimiento().format(dateFormatter));
                System.out.println("Obra Social: " + paciente.getObraSocial());
                System.out.println("Estado: " + (paciente.isActivo() ? "Activo" : "Inactivo"));
            }
        }
    }

    private void menuGestionTurnos() {
        System.out.println("\n--- GESTIÓN DE TURNOS ---");
        System.out.println("1. Listar Todos los Turnos");
        System.out.println("2. Cancelar Turno");
        System.out.println("3. Volver");
        System.out.print("\nSeleccione una opción: ");

        try {
            int opcion = Integer.parseInt(scanner.nextLine().trim());

            switch (opcion) {
                case 1:
                    listarTurnos();
                    break;
                case 2:
                    cancelarTurno();
                    break;
                case 3:
                    break;
                default:
                    System.out.println("\nOpción inválida.");
            }
        } catch (NumberFormatException e) {
            System.out.println("\nDebe ingresar un número válido.");
        }
    }

    private void listarTurnos() {
        System.out.println("\n--- LISTA DE TURNOS ---");
        List<Turno> turnos = turnoService.obtenerTodosTurnos();

        if (turnos.isEmpty()) {
            System.out.println("No hay turnos registrados.");
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            for (Turno turno : turnos) {
                System.out.println("\n" + "─".repeat(50));
                System.out.println("ID: " + turno.getId());
                System.out.println("Paciente: " + turno.getPaciente().getUsuario().getNombreCompleto());
                System.out.println("Médico: " + turno.getMedico().getUsuario().getNombreCompleto());
                System.out.println("Especialidad: " + turno.getMedico().getEspecialidad().getNombre());
                System.out.println("Fecha y Hora: " + turno.getFechaHora().format(formatter));
                System.out.println("Estado: " + turno.getEstado().getNombre());
                if (turno.getMotivoConsulta() != null) {
                    System.out.println("Motivo: " + turno.getMotivoConsulta());
                }
            }
            System.out.println("─".repeat(50));
        }
    }

    private void cancelarTurno() {
        System.out.print("\nID del turno a cancelar: ");
        Long turnoId = Long.parseLong(scanner.nextLine().trim());
        
        System.out.print("Motivo de cancelación: ");
        String motivo = scanner.nextLine().trim();

        turnoService.cancelarTurno(turnoId, motivo);
        System.out.println("\n¡Turno cancelado exitosamente!");
    }

    private void visualizarDisponibilidad() {
        System.out.println("\n--- VISUALIZAR DISPONIBILIDAD ---");
        
        System.out.println("\nEspecialidades disponibles:");
        int i = 1;
        for (Especialidad esp : Especialidad.values()) {
            System.out.println(i++ + ". " + esp.getNombre());
        }
        System.out.print("Seleccione especialidad (0 para ver todos): ");
        int espIndex = Integer.parseInt(scanner.nextLine().trim());
        
        List<Medico> medicos;
        if (espIndex == 0) {
            medicos = usuarioService.obtenerTodosMedicos();
        } else {
            Especialidad especialidad = Especialidad.values()[espIndex - 1];
            medicos = usuarioService.obtenerMedicosPorEspecialidad(especialidad);
        }

        if (medicos.isEmpty()) {
            System.out.println("\nNo hay médicos disponibles.");
            return;
        }

        System.out.println("\nMédicos:");
        i = 1;
        for (Medico medico : medicos) {
            System.out.println(i++ + ". " + medico);
        }
        
        System.out.print("\nSeleccione médico: ");
        int medicoIndex = Integer.parseInt(scanner.nextLine().trim()) - 1;
        Medico medico = medicos.get(medicoIndex);
        
        System.out.print("Fecha (dd/MM/yyyy): ");
        String fechaStr = scanner.nextLine().trim();
        LocalDate fecha = LocalDate.parse(fechaStr, dateFormatter);

        var disponibilidad = turnoService.obtenerDisponibilidad(medico.getId(), fecha);

        if (disponibilidad.isEmpty()) {
            System.out.println("\nNo hay turnos disponibles para esa fecha.");
        } else {
            System.out.println("\nTurnos disponibles:");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            for (var horario : disponibilidad) {
                System.out.println("- " + horario.format(formatter));
            }
        }
    }
}
