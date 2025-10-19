package com.turnossalud;

import com.turnossalud.dao.*;
import com.turnossalud.model.*;
import com.turnossalud.service.*;
import com.turnossalud.ui.*;
import java.time.LocalDate;
import java.util.Arrays;

/**
 * Clase principal de la aplicación de gestión de turnos médicos.
 * Implementa una arquitectura de tres capas:
 * - Capa de Presentación (UI)
 * - Capa de Lógica de Negocio (Service)
 * - Capa de Acceso a Datos (DAO)
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("Iniciando Sistema de Gestión de Turnos Médicos...");
        
        // Inicializar Capa de Acceso a Datos (Data Access Layer)
        UsuarioDAO usuarioDAO = new UsuarioDAOImpl();
        MedicoDAO medicoDAO = new MedicoDAOImpl();
        PacienteDAO pacienteDAO = new PacienteDAOImpl();
        TurnoDAO turnoDAO = new TurnoDAOImpl();

        // Inicializar Capa de Lógica de Negocio (Business Logic Layer)
        AutenticacionService autenticacionService = new AutenticacionService(usuarioDAO);
        UsuarioService usuarioService = new UsuarioService(usuarioDAO, medicoDAO, pacienteDAO);
        TurnoService turnoService = new TurnoService(turnoDAO, medicoDAO);

        // Inicializar Capa de Presentación (Presentation Layer)
        MenuAdministrativo menuAdministrativo = new MenuAdministrativo(
            autenticacionService, usuarioService, turnoService
        );
        MenuMedico menuMedico = new MenuMedico(
            autenticacionService, usuarioService, turnoService
        );
        MenuPaciente menuPaciente = new MenuPaciente(
            autenticacionService, usuarioService, turnoService
        );
        MenuPrincipal menuPrincipal = new MenuPrincipal(
            autenticacionService, menuAdministrativo, menuMedico, menuPaciente
        );

        // Cargar datos de prueba
        cargarDatosDePrueba(usuarioService);

        System.out.println("\n=== Sistema iniciado correctamente ===");
        System.out.println("\nDatos de prueba cargados:");
        System.out.println("- Usuario Administrativo: admin / admin123");
        System.out.println("- Usuario Médico: drperez / medico123");
        System.out.println("- Usuario Paciente: jgarcia / paciente123");
        System.out.println("\n");

        // Mostrar menú principal
        menuPrincipal.mostrar();
        
        // Cerrar recursos
        menuPrincipal.cerrar();
        
        System.out.println("\nSistema finalizado.");
    }

    /**
     * Carga datos de prueba en el sistema.
     */
    private static void cargarDatosDePrueba(UsuarioService usuarioService) {
        try {
            // Crear usuario administrativo
            Usuario admin = usuarioService.registrarUsuario(
                "admin", "admin123", "Carlos", "López", "20123456",
                "admin@turnossalud.com", "1234567890", Rol.ADMINISTRATIVO
            );

            // Crear médicos
            Usuario usuarioMedico1 = usuarioService.registrarUsuario(
                "drperez", "medico123", "María", "Pérez", "25123456",
                "mperez@turnossalud.com", "1134567890", Rol.MEDICO
            );
            Medico medico1 = usuarioService.registrarMedico(
                usuarioMedico1, "MP-12345", Especialidad.CLINICA_GENERAL,
                Arrays.asList("Lunes", "Martes", "Miércoles", "Jueves", "Viernes"),
                "09:00", "17:00"
            );

            Usuario usuarioMedico2 = usuarioService.registrarUsuario(
                "drramirez", "medico123", "José", "Ramírez", "27123456",
                "jramirez@turnossalud.com", "1145678901", Rol.MEDICO
            );
            Medico medico2 = usuarioService.registrarMedico(
                usuarioMedico2, "MP-67890", Especialidad.PEDIATRIA,
                Arrays.asList("Lunes", "Miércoles", "Viernes"),
                "10:00", "16:00"
            );

            Usuario usuarioMedico3 = usuarioService.registrarUsuario(
                "drgonzalez", "medico123", "Ana", "González", "28123456",
                "agonzalez@turnossalud.com", "1156789012", Rol.MEDICO
            );
            Medico medico3 = usuarioService.registrarMedico(
                usuarioMedico3, "MP-11223", Especialidad.CARDIOLOGIA,
                Arrays.asList("Martes", "Jueves"),
                "08:00", "14:00"
            );

            // Crear pacientes
            Usuario usuarioPaciente1 = usuarioService.registrarUsuario(
                "jgarcia", "paciente123", "Juan", "García", "30123456",
                "jgarcia@email.com", "1198765432", Rol.PACIENTE
            );
            Paciente paciente1 = usuarioService.registrarPaciente(
                usuarioPaciente1, LocalDate.of(1990, 5, 15),
                "Av. Corrientes 1234", "OSDE", "123456"
            );

            Usuario usuarioPaciente2 = usuarioService.registrarUsuario(
                "mfernandez", "paciente123", "María", "Fernández", "32123456",
                "mfernandez@email.com", "1187654321", Rol.PACIENTE
            );
            Paciente paciente2 = usuarioService.registrarPaciente(
                usuarioPaciente2, LocalDate.of(1985, 8, 22),
                "Calle Belgrano 567", "Swiss Medical", "789012"
            );

            Usuario usuarioPaciente3 = usuarioService.registrarUsuario(
                "lmartinez", "paciente123", "Luis", "Martínez", "35123456",
                "lmartinez@email.com", "1176543210", Rol.PACIENTE
            );
            Paciente paciente3 = usuarioService.registrarPaciente(
                usuarioPaciente3, LocalDate.of(2010, 3, 10),
                "Av. Santa Fe 890", "Galeno", "345678"
            );

            System.out.println("Datos de prueba cargados exitosamente.");
        } catch (Exception e) {
            System.err.println("Error al cargar datos de prueba: " + e.getMessage());
        }
    }
}
