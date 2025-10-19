package com.turnossalud.ui;

import com.turnossalud.model.Rol;
import com.turnossalud.model.Usuario;
import com.turnossalud.service.AutenticacionService;
import java.util.Scanner;

/**
 * Menú principal de la aplicación.
 */
public class MenuPrincipal {
    private final Scanner scanner;
    private final AutenticacionService autenticacionService;
    private final MenuAdministrativo menuAdministrativo;
    private final MenuMedico menuMedico;
    private final MenuPaciente menuPaciente;

    public MenuPrincipal(AutenticacionService autenticacionService,
                        MenuAdministrativo menuAdministrativo,
                        MenuMedico menuMedico,
                        MenuPaciente menuPaciente) {
        this.scanner = new Scanner(System.in);
        this.autenticacionService = autenticacionService;
        this.menuAdministrativo = menuAdministrativo;
        this.menuMedico = menuMedico;
        this.menuPaciente = menuPaciente;
    }

    public void mostrar() {
        boolean continuar = true;

        while (continuar) {
            if (!autenticacionService.estaAutenticado()) {
                mostrarMenuInicio();
            } else {
                Usuario usuario = autenticacionService.getUsuarioActual();
                mostrarMenuSegunRol(usuario);
            }

            if (!autenticacionService.estaAutenticado()) {
                System.out.println("\n¿Desea salir de la aplicación? (S/N): ");
                String respuesta = scanner.nextLine().trim();
                if (respuesta.equalsIgnoreCase("S")) {
                    continuar = false;
                }
            }
        }

        System.out.println("\n¡Hasta luego!");
    }

    private void mostrarMenuInicio() {
        System.out.println("\n╔════════════════════════════════════════════════╗");
        System.out.println("║   SISTEMA DE GESTIÓN DE TURNOS MÉDICOS        ║");
        System.out.println("╚════════════════════════════════════════════════╝");
        System.out.println("\n1. Iniciar Sesión");
        System.out.println("2. Salir");
        System.out.print("\nSeleccione una opción: ");

        try {
            int opcion = Integer.parseInt(scanner.nextLine().trim());

            switch (opcion) {
                case 1:
                    iniciarSesion();
                    break;
                case 2:
                    break;
                default:
                    System.out.println("\nOpción inválida.");
            }
        } catch (NumberFormatException e) {
            System.out.println("\nDebe ingresar un número válido.");
        }
    }

    private void iniciarSesion() {
        System.out.println("\n--- INICIO DE SESIÓN ---");
        System.out.print("Nombre de usuario: ");
        String nombreUsuario = scanner.nextLine().trim();

        System.out.print("Contraseña: ");
        String contrasena = scanner.nextLine().trim();

        if (autenticacionService.iniciarSesion(nombreUsuario, contrasena)) {
            Usuario usuario = autenticacionService.getUsuarioActual();
            System.out.println("\n¡Bienvenido " + usuario.getNombreCompleto() + "!");
            System.out.println("Rol: " + usuario.getRol().getNombre());
        } else {
            System.out.println("\nCredenciales inválidas. Por favor, intente nuevamente.");
        }
    }

    private void mostrarMenuSegunRol(Usuario usuario) {
        if (usuario.getRol() == Rol.ADMINISTRATIVO) {
            menuAdministrativo.mostrar();
        } else if (usuario.getRol() == Rol.MEDICO) {
            menuMedico.mostrar();
        } else if (usuario.getRol() == Rol.PACIENTE) {
            menuPaciente.mostrar();
        }
    }

    public void cerrar() {
        scanner.close();
    }
}
