package com.turnossalud.ui;

import com.turnossalud.model.*;
import com.turnossalud.service.AutenticacionService;
import com.turnossalud.service.UsuarioService;
import com.turnossalud.service.TurnoService;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/**
 * Menú para usuarios con rol Médico.
 */
public class MenuMedico {
    private final Scanner scanner;
    private final AutenticacionService autenticacionService;
    private final UsuarioService usuarioService;
    private final TurnoService turnoService;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public MenuMedico(AutenticacionService autenticacionService,
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
            System.out.println("║            MENÚ MÉDICO                         ║");
            System.out.println("╚════════════════════════════════════════════════╝");
            System.out.println("\n1. Ver Mis Turnos");
            System.out.println("2. Completar Turno");
            System.out.println("3. Ver Turnos del Día");
            System.out.println("4. Cerrar Sesión");
            System.out.print("\nSeleccione una opción: ");

            try {
                int opcion = Integer.parseInt(scanner.nextLine().trim());

                switch (opcion) {
                    case 1:
                        verMisTurnos();
                        break;
                    case 2:
                        completarTurno();
                        break;
                    case 3:
                        verTurnosDelDia();
                        break;
                    case 4:
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

    private void verMisTurnos() {
        Usuario usuario = autenticacionService.getUsuarioActual();
        Optional<Medico> medicoOpt = usuarioService.buscarMedicoPorUsuarioId(usuario.getId());

        if (medicoOpt.isEmpty()) {
            System.out.println("\nError: No se encontró información del médico.");
            return;
        }

        Medico medico = medicoOpt.get();
        List<Turno> turnos = turnoService.obtenerTurnosMedico(medico.getId());

        System.out.println("\n--- MIS TURNOS ---");
        if (turnos.isEmpty()) {
            System.out.println("No tiene turnos asignados.");
        } else {
            for (Turno turno : turnos) {
                System.out.println("\n" + "─".repeat(50));
                System.out.println("ID: " + turno.getId());
                System.out.println("Paciente: " + turno.getPaciente().getUsuario().getNombreCompleto());
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

    private void completarTurno() {
        System.out.print("\nID del turno a completar: ");
        Long turnoId = Long.parseLong(scanner.nextLine().trim());

        System.out.print("Observaciones: ");
        String observaciones = scanner.nextLine().trim();

        turnoService.completarTurno(turnoId, observaciones);
        System.out.println("\n¡Turno completado exitosamente!");
    }

    private void verTurnosDelDia() {
        Usuario usuario = autenticacionService.getUsuarioActual();
        Optional<Medico> medicoOpt = usuarioService.buscarMedicoPorUsuarioId(usuario.getId());

        if (medicoOpt.isEmpty()) {
            System.out.println("\nError: No se encontró información del médico.");
            return;
        }

        Medico medico = medicoOpt.get();
        LocalDate hoy = LocalDate.now();
        
        List<Turno> turnos = turnoService.obtenerTurnosMedico(medico.getId());
        List<Turno> turnosHoy = turnos.stream()
                .filter(t -> t.getFechaHora().toLocalDate().equals(hoy))
                .toList();

        System.out.println("\n--- TURNOS DEL DÍA (" + hoy.format(dateFormatter) + ") ---");
        if (turnosHoy.isEmpty()) {
            System.out.println("No tiene turnos para el día de hoy.");
        } else {
            for (Turno turno : turnosHoy) {
                System.out.println("\n" + "─".repeat(50));
                System.out.println("ID: " + turno.getId());
                System.out.println("Hora: " + turno.getFechaHora().toLocalTime());
                System.out.println("Paciente: " + turno.getPaciente().getUsuario().getNombreCompleto());
                System.out.println("DNI: " + turno.getPaciente().getUsuario().getDni());
                System.out.println("Estado: " + turno.getEstado().getNombre());
                if (turno.getMotivoConsulta() != null) {
                    System.out.println("Motivo: " + turno.getMotivoConsulta());
                }
            }
            System.out.println("─".repeat(50));
        }
    }
}
