package com.turnossalud.dao;

import com.turnossalud.model.Paciente;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * Implementación en memoria del DAO de Paciente.
 */
public class PacienteDAOImpl implements PacienteDAO {
    private final Map<Long, Paciente> pacientes = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    @Override
    public Paciente guardar(Paciente paciente) {
        if (paciente.getId() == null) {
            paciente.setId(idGenerator.getAndIncrement());
        }
        pacientes.put(paciente.getId(), paciente);
        return paciente;
    }

    @Override
    public Optional<Paciente> buscarPorId(Long id) {
        return Optional.ofNullable(pacientes.get(id));
    }

    @Override
    public Optional<Paciente> buscarPorUsuarioId(Long usuarioId) {
        return pacientes.values().stream()
                .filter(p -> p.getUsuario().getId().equals(usuarioId))
                .findFirst();
    }

    @Override
    public List<Paciente> buscarTodos() {
        return new ArrayList<>(pacientes.values());
    }

    @Override
    public List<Paciente> buscarActivos() {
        return pacientes.values().stream()
                .filter(Paciente::isActivo)
                .collect(Collectors.toList());
    }

    @Override
    public void actualizar(Paciente paciente) {
        if (paciente.getId() != null && pacientes.containsKey(paciente.getId())) {
            pacientes.put(paciente.getId(), paciente);
        }
    }

    @Override
    public void eliminar(Long id) {
        pacientes.remove(id);
    }
}
