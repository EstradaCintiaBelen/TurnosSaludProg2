package com.turnossalud.dao;

import com.turnossalud.model.Paciente;
import java.util.List;
import java.util.Optional;

/**
 * Interfaz DAO para operaciones con Paciente.
 */
public interface PacienteDAO {
    Paciente guardar(Paciente paciente);
    Optional<Paciente> buscarPorId(Long id);
    Optional<Paciente> buscarPorUsuarioId(Long usuarioId);
    List<Paciente> buscarTodos();
    List<Paciente> buscarActivos();
    void actualizar(Paciente paciente);
    void eliminar(Long id);
}
