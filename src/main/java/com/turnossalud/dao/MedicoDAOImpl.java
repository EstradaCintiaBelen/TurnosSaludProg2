package com.turnossalud.dao;

import com.turnossalud.model.Medico;
import com.turnossalud.model.Especialidad;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * Implementación en memoria del DAO de Medico.
 */
public class MedicoDAOImpl implements MedicoDAO {
    private final Map<Long, Medico> medicos = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    @Override
    public Medico guardar(Medico medico) {
        if (medico.getId() == null) {
            medico.setId(idGenerator.getAndIncrement());
        }
        medicos.put(medico.getId(), medico);
        return medico;
    }

    @Override
    public Optional<Medico> buscarPorId(Long id) {
        return Optional.ofNullable(medicos.get(id));
    }

    @Override
    public Optional<Medico> buscarPorUsuarioId(Long usuarioId) {
        return medicos.values().stream()
                .filter(m -> m.getUsuario().getId().equals(usuarioId))
                .findFirst();
    }

    @Override
    public Optional<Medico> buscarPorMatricula(String matricula) {
        return medicos.values().stream()
                .filter(m -> m.getMatricula().equals(matricula))
                .findFirst();
    }

    @Override
    public List<Medico> buscarTodos() {
        return new ArrayList<>(medicos.values());
    }

    @Override
    public List<Medico> buscarPorEspecialidad(Especialidad especialidad) {
        return medicos.values().stream()
                .filter(m -> m.getEspecialidad() == especialidad)
                .collect(Collectors.toList());
    }

    @Override
    public List<Medico> buscarActivos() {
        return medicos.values().stream()
                .filter(Medico::isActivo)
                .collect(Collectors.toList());
    }

    @Override
    public void actualizar(Medico medico) {
        if (medico.getId() != null && medicos.containsKey(medico.getId())) {
            medicos.put(medico.getId(), medico);
        }
    }

    @Override
    public void eliminar(Long id) {
        medicos.remove(id);
    }
}
