package com.turnossalud.service;

import com.turnossalud.dao.UsuarioDAO;
import com.turnossalud.dao.MedicoDAO;
import com.turnossalud.dao.PacienteDAO;
import com.turnossalud.model.*;
import java.util.List;
import java.util.Optional;

/**
 * Servicio para gestionar usuarios y personal.
 */
public class UsuarioService {
    private final UsuarioDAO usuarioDAO;
    private final MedicoDAO medicoDAO;
    private final PacienteDAO pacienteDAO;

    public UsuarioService(UsuarioDAO usuarioDAO, MedicoDAO medicoDAO, PacienteDAO pacienteDAO) {
        this.usuarioDAO = usuarioDAO;
        this.medicoDAO = medicoDAO;
        this.pacienteDAO = pacienteDAO;
    }

    /**
     * Registra un nuevo usuario en el sistema.
     */
    public Usuario registrarUsuario(String nombreUsuario, String contrasena, String nombre,
                                   String apellido, String dni, String email, String telefono, Rol rol) {
        if (usuarioDAO.existeNombreUsuario(nombreUsuario)) {
            throw new IllegalArgumentException("El nombre de usuario ya existe");
        }

        if (usuarioDAO.buscarPorDni(dni).isPresent()) {
            throw new IllegalArgumentException("Ya existe un usuario con ese DNI");
        }

        Usuario usuario = new Usuario(nombreUsuario, contrasena, nombre, apellido, dni, rol);
        usuario.setEmail(email);
        usuario.setTelefono(telefono);

        return usuarioDAO.guardar(usuario);
    }

    /**
     * Registra un médico con su información profesional.
     */
    public Medico registrarMedico(Usuario usuario, String matricula, Especialidad especialidad,
                                 List<String> diasAtencion, String horarioInicio, String horarioFin) {
        if (usuario.getRol() != Rol.MEDICO) {
            throw new IllegalArgumentException("El usuario debe tener rol de médico");
        }

        if (medicoDAO.buscarPorMatricula(matricula).isPresent()) {
            throw new IllegalArgumentException("Ya existe un médico con esa matrícula");
        }

        Medico medico = new Medico(usuario, matricula, especialidad);
        medico.setDiasAtencion(diasAtencion);
        medico.setHorarioInicio(horarioInicio);
        medico.setHorarioFin(horarioFin);

        return medicoDAO.guardar(medico);
    }

    /**
     * Registra un paciente con su información médica.
     */
    public Paciente registrarPaciente(Usuario usuario, java.time.LocalDate fechaNacimiento,
                                     String direccion, String obraSocial, String numeroAfiliado) {
        if (usuario.getRol() != Rol.PACIENTE) {
            throw new IllegalArgumentException("El usuario debe tener rol de paciente");
        }

        Paciente paciente = new Paciente(usuario, fechaNacimiento);
        paciente.setDireccion(direccion);
        paciente.setObraSocial(obraSocial);
        paciente.setNumeroAfiliado(numeroAfiliado);

        return pacienteDAO.guardar(paciente);
    }

    /**
     * Obtiene todos los usuarios.
     */
    public List<Usuario> obtenerTodosUsuarios() {
        return usuarioDAO.buscarTodos();
    }

    /**
     * Obtiene usuarios por rol.
     */
    public List<Usuario> obtenerUsuariosPorRol(Rol rol) {
        return usuarioDAO.buscarPorRol(rol);
    }

    /**
     * Obtiene todos los médicos.
     */
    public List<Medico> obtenerTodosMedicos() {
        return medicoDAO.buscarTodos();
    }

    /**
     * Obtiene médicos por especialidad.
     */
    public List<Medico> obtenerMedicosPorEspecialidad(Especialidad especialidad) {
        return medicoDAO.buscarPorEspecialidad(especialidad);
    }

    /**
     * Obtiene todos los pacientes.
     */
    public List<Paciente> obtenerTodosPacientes() {
        return pacienteDAO.buscarTodos();
    }

    /**
     * Busca un médico por ID de usuario.
     */
    public Optional<Medico> buscarMedicoPorUsuarioId(Long usuarioId) {
        return medicoDAO.buscarPorUsuarioId(usuarioId);
    }

    /**
     * Busca un paciente por ID de usuario.
     */
    public Optional<Paciente> buscarPacientePorUsuarioId(Long usuarioId) {
        return pacienteDAO.buscarPorUsuarioId(usuarioId);
    }

    /**
     * Busca un usuario por ID.
     */
    public Optional<Usuario> buscarUsuarioPorId(Long id) {
        return usuarioDAO.buscarPorId(id);
    }

    /**
     * Actualiza la información de un usuario.
     */
    public void actualizarUsuario(Usuario usuario) {
        usuarioDAO.actualizar(usuario);
    }

    /**
     * Desactiva un usuario.
     */
    public void desactivarUsuario(Long id) {
        Optional<Usuario> usuario = usuarioDAO.buscarPorId(id);
        if (usuario.isPresent()) {
            Usuario u = usuario.get();
            u.setActivo(false);
            usuarioDAO.actualizar(u);
        }
    }
}
