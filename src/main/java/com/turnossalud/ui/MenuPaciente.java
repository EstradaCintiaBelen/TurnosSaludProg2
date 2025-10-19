package com.turnossalud.ui;

import com.turnossalud.model.*;
import com.turnossalud.service.AutenticacionService;
import com.turnossalud.service.UsuarioService;
import com.turnossalud.service.TurnoService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/**
 * Menú para usuarios con rol Paciente.
 */
public class MenuPaciente {
    private final Scanner scanner;
    private final AutenticacionService autenticacionService;
    private final UsuarioService usuarioService;
    private final TurnoService turnoService;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    public MenuPaciente(AutenticacionService autenticacionService,
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
            System.out.println("║           MENÚ PACIENTE                        ║");
            System.out.println("╚════════════════════════════════════════════════╝");
            System.out.println("\n1. Solicitar Turno");
            System.out.println("2. Ver Mis Turnos");
            System.out.println("3. Cancelar Turno");
            System.out.println("4. Ver Disponibilidad");
            System.out.println("5. Cerrar Sesión");
            System.out.print("\nSeleccione una opción: ");

            try {
                int opcion = Integer.parseInt(scanner.nextLine().trim());

                switch (opcion) {
                    case 1:
                        solicitarTurno();
                        break;
                    case 2:
                        verMisTurnos();
                        break;
                    case 3:
                        cancelarTurno();
                        break;
                    case 4:
                        verDisponibilidad();
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

    private void solicitarTurno() {
        Usuario usuario = autenticacionService.getUsuarioActual();
        Optional<Paciente> pacienteOpt = usuarioService.buscarPacientePorUsuarioId(usuario.getId());

        if (pacienteOpt.isEmpty()) {
            System.out.println("\nError: No se encontró información del paciente.");
            return;
        }

        Paciente paciente = pacienteOpt.get();

        System.out.println("\n--- SOLICITAR TURNO ---");
        
        // Seleccionar especialidad
        System.out.println("\nEspecialidades disponibles:");
        int i = 1;
        for (Especialidad esp : Especialidad.values()) {
            System.out.println(i++ + ". " + esp.getNombre());
        }
        System.out.print("Seleccione especialidad: ");
        int espIndex = Integer.parseInt(scanner.nextLine().trim()) - 1;
        Especialidad especialidad = Especialidad.values()[espIndex];

        // Seleccionar médico
        List<Medico> medicos = usuarioService.obtenerMedicosPorEspecialidad(especialidad);
        if (medicos.isEmpty()) {
            System.out.println("\nNo hay médicos disponibles para esa especialidad.");
            return;
        }

        System.out.println("\nMédicos disponibles:");
        i = 1;
        for (Medico medico : medicos) {
            System.out.println(i++ + ". " + medico);
        }
        System.out.print("Seleccione médico: ");
        int medicoIndex = Integer.parseInt(scanner.nextLine().trim()) - 1;
        Medico medico = medicos.get(medicoIndex);

        // Seleccionar fecha
        System.out.print("\nFecha del turno (dd/MM/yyyy): ");
        String fechaStr = scanner.nextLine().trim();
        LocalDate fecha = LocalDate.parse(fechaStr, dateFormatter);

        // Mostrar horarios disponibles
        List<LocalDateTime> horariosDisponibles = turnoService.obtenerDisponibilidad(medico.getId(), fecha);
        if (horariosDisponibles.isEmpty()) {
            System.out.println("\nNo hay horarios disponibles para esa fecha.");
            return;
        }

        System.out.println("\nHorarios disponibles:");
        i = 1;
        for (LocalDateTime horario : horariosDisponibles) {
            System.out.println(i++ + ". " + horario.format(timeFormatter));
        }
        System.out.print("Seleccione horario: ");
        int horarioIndex = Integer.parseInt(scanner.nextLine().trim()) - 1;
        LocalDateTime fechaHora = horariosDisponibles.get(horarioIndex);

        // Motivo de consulta
        System.out.print("\nMotivo de la consulta: ");
        String motivo = scanner.nextLine().trim();

        // Crear turno
        Turno turno = turnoService.crearTurno(paciente, medico, fechaHora, motivo);
        System.out.println("\n¡Turno solicitado exitosamente!");
        System.out.println("ID del turno: " + turno.getId());
        System.out.println("Médico: " + medico.getUsuario().getNombreCompleto());
        System.out.println("Fecha y Hora: " + fechaHora.format(dateTimeFormatter));
    }

    private void verMisTurnos() {
        Usuario usuario = autenticacionService.getUsuarioActual();
        Optional<Paciente> pacienteOpt = usuarioService.buscarPacientePorUsuarioId(usuario.getId());

        if (pacienteOpt.isEmpty()) {
            System.out.println("\nError: No se encontró información del paciente.");
            return;
        }

        Paciente paciente = pacienteOpt.get();
        List<Turno> turnos = turnoService.obtenerTurnosPaciente(paciente.getId());

        System.out.println("\n--- MIS TURNOS ---");
        if (turnos.isEmpty()) {
            System.out.println("No tiene turnos registrados.");
        } else {
            for (Turno turno : turnos) {
                System.out.println("\n" + "─".repeat(50));
                System.out.println("ID: " + turno.getId());
                System.out.println("Médico: " + turno.getMedico().getUsuario().getNombreCompleto());
                System.out.println("Especialidad: " + turno.getMedico().getEspecialidad().getNombre());
                System.out.println("Fecha y Hora: " + turno.getFechaHora().format(dateTimeFormatter));
                System.out.println("Estado: " + turno.getEstado().getNombre());
                if (turno.getMotivoConsulta() != null) {
                    System.out.println("Motivo: " + turno.getMotivoConsulta());
                }
                if (turno.getObservaciones() != null) {
                    System.out.println("Observaciones: " + turno.getObservaciones());
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

    private void verDisponibilidad() {
        System.out.println("\n--- VER DISPONIBILIDAD ---");
        
        // Seleccionar especialidad
        System.out.println("\nEspecialidades disponibles:");
        int i = 1;
        for (Especialidad esp : Especialidad.values()) {
            System.out.println(i++ + ". " + esp.getNombre());
        }
        System.out.print("Seleccione especialidad: ");
        int espIndex = Integer.parseInt(scanner.nextLine().trim()) - 1;
        Especialidad especialidad = Especialidad.values()[espIndex];

        // Seleccionar médico
        List<Medico> medicos = usuarioService.obtenerMedicosPorEspecialidad(especialidad);
        if (medicos.isEmpty()) {
            System.out.println("\nNo hay médicos disponibles para esa especialidad.");
            return;
        }

        System.out.println("\nMédicos disponibles:");
        i = 1;
        for (Medico medico : medicos) {
            System.out.println(i++ + ". " + medico);
        }
        System.out.print("Seleccione médico: ");
        int medicoIndex = Integer.parseInt(scanner.nextLine().trim()) - 1;
        Medico medico = medicos.get(medicoIndex);

        // Seleccionar fecha
        System.out.print("\nFecha (dd/MM/yyyy): ");
        String fechaStr = scanner.nextLine().trim();
        LocalDate fecha = LocalDate.parse(fechaStr, dateFormatter);

        // Mostrar disponibilidad
        List<LocalDateTime> horariosDisponibles = turnoService.obtenerDisponibilidad(medico.getId(), fecha);
        
        System.out.println("\n--- DISPONIBILIDAD ---");
        System.out.println("Médico: " + medico.getUsuario().getNombreCompleto());
        System.out.println("Especialidad: " + medico.getEspecialidad().getNombre());
        System.out.println("Fecha: " + fecha.format(dateFormatter));
        
        if (horariosDisponibles.isEmpty()) {
            System.out.println("\nNo hay horarios disponibles para esa fecha.");
        } else {
            System.out.println("\nHorarios disponibles:");
            for (LocalDateTime horario : horariosDisponibles) {
                System.out.println("- " + horario.format(timeFormatter));
            }
        }
    }
}
