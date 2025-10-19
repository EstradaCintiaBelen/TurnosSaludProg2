package com.turnossalud.dao;

import com.turnossalud.model.Medico;
import com.turnossalud.model.Especialidad;
import java.util.List;
import java.util.Optional;

/**
 * Interfaz DAO para operaciones con Medico.
 */
public interface MedicoDAO {
    Medico guardar(Medico medico);
    Optional<Medico> buscarPorId(Long id);
    Optional<Medico> buscarPorUsuarioId(Long usuarioId);
    Optional<Medico> buscarPorMatricula(String matricula);
    List<Medico> buscarTodos();
    List<Medico> buscarPorEspecialidad(Especialidad especialidad);
    List<Medico> buscarActivos();
    void actualizar(Medico medico);
    void eliminar(Long id);
}
