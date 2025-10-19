package com.turnossalud.service;

import com.turnossalud.dao.UsuarioDAO;
import com.turnossalud.model.Usuario;
import java.util.Optional;

/**
 * Servicio para gestionar la autenticación de usuarios.
 */
public class AutenticacionService {
    private final UsuarioDAO usuarioDAO;
    private Usuario usuarioActual;

    public AutenticacionService(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    /**
     * Inicia sesión con las credenciales proporcionadas.
     */
    public boolean iniciarSesion(String nombreUsuario, String contrasena) {
        if (usuarioDAO.validarCredenciales(nombreUsuario, contrasena)) {
            Optional<Usuario> usuario = usuarioDAO.buscarPorNombreUsuario(nombreUsuario);
            if (usuario.isPresent()) {
                usuarioActual = usuario.get();
                return true;
            }
        }
        return false;
    }

    /**
     * Cierra la sesión del usuario actual.
     */
    public void cerrarSesion() {
        usuarioActual = null;
    }

    /**
     * Obtiene el usuario actualmente autenticado.
     */
    public Usuario getUsuarioActual() {
        return usuarioActual;
    }

    /**
     * Verifica si hay un usuario autenticado.
     */
    public boolean estaAutenticado() {
        return usuarioActual != null;
    }

    /**
     * Verifica si el usuario actual tiene el rol especificado.
     */
    public boolean tieneRol(com.turnossalud.model.Rol rol) {
        return usuarioActual != null && usuarioActual.getRol() == rol;
    }
}
