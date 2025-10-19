package com.turnossalud.dao;

import com.turnossalud.model.Usuario;
import com.turnossalud.model.Rol;
import java.util.List;
import java.util.Optional;

/**
 * Interfaz DAO para operaciones con Usuario.
 */
public interface UsuarioDAO {
    Usuario guardar(Usuario usuario);
    Optional<Usuario> buscarPorId(Long id);
    Optional<Usuario> buscarPorNombreUsuario(String nombreUsuario);
    Optional<Usuario> buscarPorDni(String dni);
    List<Usuario> buscarTodos();
    List<Usuario> buscarPorRol(Rol rol);
    void actualizar(Usuario usuario);
    void eliminar(Long id);
    boolean existeNombreUsuario(String nombreUsuario);
    boolean validarCredenciales(String nombreUsuario, String contrasena);
}
