package com.turnossalud.dao;

import com.turnossalud.model.Usuario;
import com.turnossalud.model.Rol;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * Implementación en memoria del DAO de Usuario.
 */
public class UsuarioDAOImpl implements UsuarioDAO {
    private final Map<Long, Usuario> usuarios = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    @Override
    public Usuario guardar(Usuario usuario) {
        if (usuario.getId() == null) {
            usuario.setId(idGenerator.getAndIncrement());
        }
        usuarios.put(usuario.getId(), usuario);
        return usuario;
    }

    @Override
    public Optional<Usuario> buscarPorId(Long id) {
        return Optional.ofNullable(usuarios.get(id));
    }

    @Override
    public Optional<Usuario> buscarPorNombreUsuario(String nombreUsuario) {
        return usuarios.values().stream()
                .filter(u -> u.getNombreUsuario().equals(nombreUsuario))
                .findFirst();
    }

    @Override
    public Optional<Usuario> buscarPorDni(String dni) {
        return usuarios.values().stream()
                .filter(u -> u.getDni().equals(dni))
                .findFirst();
    }

    @Override
    public List<Usuario> buscarTodos() {
        return new ArrayList<>(usuarios.values());
    }

    @Override
    public List<Usuario> buscarPorRol(Rol rol) {
        return usuarios.values().stream()
                .filter(u -> u.getRol() == rol)
                .collect(Collectors.toList());
    }

    @Override
    public void actualizar(Usuario usuario) {
        if (usuario.getId() != null && usuarios.containsKey(usuario.getId())) {
            usuarios.put(usuario.getId(), usuario);
        }
    }

    @Override
    public void eliminar(Long id) {
        usuarios.remove(id);
    }

    @Override
    public boolean existeNombreUsuario(String nombreUsuario) {
        return usuarios.values().stream()
                .anyMatch(u -> u.getNombreUsuario().equals(nombreUsuario));
    }

    @Override
    public boolean validarCredenciales(String nombreUsuario, String contrasena) {
        return buscarPorNombreUsuario(nombreUsuario)
                .map(u -> u.getContrasena().equals(contrasena) && u.isActivo())
                .orElse(false);
    }
}
