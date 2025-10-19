package com.turnossalud.dao;

import com.turnossalud.model.Turno;
import com.turnossalud.model.EstadoTurno;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * Implementación en memoria del DAO de Turno.
 */
public class TurnoDAOImpl implements TurnoDAO {
    private final Map<Long, Turno> turnos = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    @Override
    public Turno guardar(Turno turno) {
        if (turno.getId() == null) {
            turno.setId(idGenerator.getAndIncrement());
        }
        turnos.put(turno.getId(), turno);
        return turno;
    }

    @Override
    public Optional<Turno> buscarPorId(Long id) {
        return Optional.ofNullable(turnos.get(id));
    }

    @Override
    public List<Turno> buscarTodos() {
        return new ArrayList<>(turnos.values());
    }

    @Override
    public List<Turno> buscarPorPaciente(Long pacienteId) {
        return turnos.values().stream()
                .filter(t -> t.getPaciente().getId().equals(pacienteId))
                .sorted(Comparator.comparing(Turno::getFechaHora).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public List<Turno> buscarPorMedico(Long medicoId) {
        return turnos.values().stream()
                .filter(t -> t.getMedico().getId().equals(medicoId))
                .sorted(Comparator.comparing(Turno::getFechaHora))
                .collect(Collectors.toList());
    }

    @Override
    public List<Turno> buscarPorEstado(EstadoTurno estado) {
        return turnos.values().stream()
                .filter(t -> t.getEstado() == estado)
                .collect(Collectors.toList());
    }

    @Override
    public List<Turno> buscarPorFecha(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return turnos.values().stream()
                .filter(t -> !t.getFechaHora().isBefore(fechaInicio) && 
                            !t.getFechaHora().isAfter(fechaFin))
                .sorted(Comparator.comparing(Turno::getFechaHora))
                .collect(Collectors.toList());
    }

    @Override
    public List<Turno> buscarPorMedicoYFecha(Long medicoId, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return turnos.values().stream()
                .filter(t -> t.getMedico().getId().equals(medicoId) &&
                            !t.getFechaHora().isBefore(fechaInicio) && 
                            !t.getFechaHora().isAfter(fechaFin) &&
                            t.getEstado() != EstadoTurno.CANCELADO)
                .sorted(Comparator.comparing(Turno::getFechaHora))
                .collect(Collectors.toList());
    }

    @Override
    public boolean existeTurno(Long medicoId, LocalDateTime fechaHora) {
        return turnos.values().stream()
                .anyMatch(t -> t.getMedico().getId().equals(medicoId) &&
                              t.getFechaHora().equals(fechaHora) &&
                              t.getEstado() != EstadoTurno.CANCELADO);
    }

    @Override
    public void actualizar(Turno turno) {
        if (turno.getId() != null && turnos.containsKey(turno.getId())) {
            turnos.put(turno.getId(), turno);
        }
    }

    @Override
    public void eliminar(Long id) {
        turnos.remove(id);
    }
}
