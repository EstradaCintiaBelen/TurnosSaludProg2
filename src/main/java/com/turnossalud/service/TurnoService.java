package com.turnossalud.service;

import com.turnossalud.dao.TurnoDAO;
import com.turnossalud.dao.MedicoDAO;
import com.turnossalud.model.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Servicio para gestionar turnos médicos.
 */
public class TurnoService {
    private final TurnoDAO turnoDAO;
    private final MedicoDAO medicoDAO;

    public TurnoService(TurnoDAO turnoDAO, MedicoDAO medicoDAO) {
        this.turnoDAO = turnoDAO;
        this.medicoDAO = medicoDAO;
    }

    /**
     * Crea un nuevo turno.
     */
    public Turno crearTurno(Paciente paciente, Medico medico, LocalDateTime fechaHora, String motivoConsulta) {
        // Validar que el turno esté en el futuro
        if (fechaHora.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("No se pueden crear turnos en el pasado");
        }

        // Validar que no exista un turno para ese médico en ese horario
        if (turnoDAO.existeTurno(medico.getId(), fechaHora)) {
            throw new IllegalArgumentException("El médico ya tiene un turno asignado en ese horario");
        }

        Turno turno = new Turno(paciente, medico, fechaHora);
        turno.setMotivoConsulta(motivoConsulta);

        return turnoDAO.guardar(turno);
    }

    /**
     * Cancela un turno.
     */
    public void cancelarTurno(Long turnoId, String motivo) {
        Optional<Turno> turnoOpt = turnoDAO.buscarPorId(turnoId);
        if (turnoOpt.isEmpty()) {
            throw new IllegalArgumentException("Turno no encontrado");
        }

        Turno turno = turnoOpt.get();
        if (turno.getEstado() == EstadoTurno.CANCELADO) {
            throw new IllegalArgumentException("El turno ya está cancelado");
        }

        if (turno.getEstado() == EstadoTurno.COMPLETADO) {
            throw new IllegalArgumentException("No se puede cancelar un turno completado");
        }

        turno.cancelar(motivo);
        turnoDAO.actualizar(turno);
    }

    /**
     * Confirma un turno.
     */
    public void confirmarTurno(Long turnoId) {
        Optional<Turno> turnoOpt = turnoDAO.buscarPorId(turnoId);
        if (turnoOpt.isEmpty()) {
            throw new IllegalArgumentException("Turno no encontrado");
        }

        Turno turno = turnoOpt.get();
        turno.confirmar();
        turnoDAO.actualizar(turno);
    }

    /**
     * Completa un turno.
     */
    public void completarTurno(Long turnoId, String observaciones) {
        Optional<Turno> turnoOpt = turnoDAO.buscarPorId(turnoId);
        if (turnoOpt.isEmpty()) {
            throw new IllegalArgumentException("Turno no encontrado");
        }

        Turno turno = turnoOpt.get();
        turno.setObservaciones(observaciones);
        turno.completar();
        turnoDAO.actualizar(turno);
    }

    /**
     * Obtiene los turnos de un paciente.
     */
    public List<Turno> obtenerTurnosPaciente(Long pacienteId) {
        return turnoDAO.buscarPorPaciente(pacienteId);
    }

    /**
     * Obtiene los turnos de un médico.
     */
    public List<Turno> obtenerTurnosMedico(Long medicoId) {
        return turnoDAO.buscarPorMedico(medicoId);
    }

    /**
     * Obtiene turnos por estado.
     */
    public List<Turno> obtenerTurnosPorEstado(EstadoTurno estado) {
        return turnoDAO.buscarPorEstado(estado);
    }

    /**
     * Obtiene la disponibilidad de turnos para un médico en una fecha específica.
     */
    public List<LocalDateTime> obtenerDisponibilidad(Long medicoId, LocalDate fecha) {
        Optional<Medico> medicoOpt = medicoDAO.buscarPorId(medicoId);
        if (medicoOpt.isEmpty()) {
            throw new IllegalArgumentException("Médico no encontrado");
        }

        Medico medico = medicoOpt.get();
        List<LocalDateTime> horariosDisponibles = new ArrayList<>();

        // Generar horarios según la configuración del médico
        if (medico.getHorarioInicio() != null && medico.getHorarioFin() != null) {
            LocalTime horaInicio = LocalTime.parse(medico.getHorarioInicio());
            LocalTime horaFin = LocalTime.parse(medico.getHorarioFin());
            int duracion = medico.getDuracionTurnoMinutos();

            LocalTime horaActual = horaInicio;
            while (horaActual.isBefore(horaFin)) {
                LocalDateTime fechaHora = LocalDateTime.of(fecha, horaActual);
                
                // Verificar si el turno no existe y está en el futuro
                if (fechaHora.isAfter(LocalDateTime.now()) && 
                    !turnoDAO.existeTurno(medicoId, fechaHora)) {
                    horariosDisponibles.add(fechaHora);
                }
                
                horaActual = horaActual.plusMinutes(duracion);
            }
        }

        return horariosDisponibles;
    }

    /**
     * Obtiene turnos por rango de fechas.
     */
    public List<Turno> obtenerTurnosPorFecha(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return turnoDAO.buscarPorFecha(fechaInicio, fechaFin);
    }

    /**
     * Obtiene todos los turnos.
     */
    public List<Turno> obtenerTodosTurnos() {
        return turnoDAO.buscarTodos();
    }

    /**
     * Busca un turno por ID.
     */
    public Optional<Turno> buscarTurnoPorId(Long id) {
        return turnoDAO.buscarPorId(id);
    }
}
