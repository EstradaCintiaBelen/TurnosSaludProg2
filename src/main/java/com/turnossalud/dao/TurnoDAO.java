package com.turnossalud.dao;

import com.turnossalud.model.Turno;
import com.turnossalud.model.EstadoTurno;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Interfaz DAO para operaciones con Turno.
 */
public interface TurnoDAO {
    Turno guardar(Turno turno);
    Optional<Turno> buscarPorId(Long id);
    List<Turno> buscarTodos();
    List<Turno> buscarPorPaciente(Long pacienteId);
    List<Turno> buscarPorMedico(Long medicoId);
    List<Turno> buscarPorEstado(EstadoTurno estado);
    List<Turno> buscarPorFecha(LocalDateTime fechaInicio, LocalDateTime fechaFin);
    List<Turno> buscarPorMedicoYFecha(Long medicoId, LocalDateTime fechaInicio, LocalDateTime fechaFin);
    boolean existeTurno(Long medicoId, LocalDateTime fechaHora);
    void actualizar(Turno turno);
    void eliminar(Long id);
}
