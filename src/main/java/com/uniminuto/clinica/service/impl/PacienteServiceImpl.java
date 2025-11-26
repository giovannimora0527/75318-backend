package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.entity.*;
import com.uniminuto.clinica.model.PacienteRq;
import com.uniminuto.clinica.model.RespuestaRs;
import com.uniminuto.clinica.repository.PacienteRepository;
import com.uniminuto.clinica.repository.UsuarioRepository;
import com.uniminuto.clinica.service.AuditoriaLoginService;
import com.uniminuto.clinica.service.AuditoriaService;
import com.uniminuto.clinica.service.PacienteService;

import java.util.List;
import java.util.Optional;

import com.uniminuto.clinica.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

/**
 *
 * @author lmora
 */
@Service
@RequiredArgsConstructor
public class PacienteServiceImpl implements PacienteService {

    private PacienteRepository pacienteRepository;

    private UsuarioService usuarioService;


    private UsuarioRepository usuarioRepository;

    private AuditoriaService auditoriaService;

    @Override
    public List<Paciente> encontrarTodosLosPacientes() {
        return this.pacienteRepository.findAll();
    }

    @Override
    public Paciente buscarPacientePorDocumento(String documento) throws BadRequestException {
        Optional<Paciente> optPaciente = this.pacienteRepository.findByNumeroDocumento(documento);
        if (!optPaciente.isPresent()) {
            throw new BadRequestException("No se encuentra el paciente");
        }
        return optPaciente.get();
    }

    @Override
    public List<Paciente> listarOrdenadoPorNombres(boolean ascendente) {
        if (ascendente) {
            return this.pacienteRepository.findAllByOrderByNombresAsc();
        } else {
            return this.pacienteRepository.findAllByOrderByNombresAsc();
        }
    }

    @Override
    public RespuestaRs guardarPaciente(PacienteRq pacienteRq) throws BadRequestException {
        Optional<Paciente> optpaciente = this.pacienteRepository.findByNumeroDocumento(
                pacienteRq.getNumeroDocumento()
        );
        if (optpaciente.isPresent()) {
            throw new BadRequestException("El número de documento ya está registrado");
        }

        Optional<Usuario> optEsp = this.usuarioRepository
                .findById(pacienteRq.getUsuario());
        if (optEsp.isEmpty()) {
            throw new BadRequestException("El usuario no existe");
        }

        Paciente pacienteGuardar = this.convertToRqToEntidad(pacienteRq, optEsp.get());
        pacienteGuardar = this.pacienteRepository.save(pacienteGuardar);
        // Registrar auditoria
        auditoriaService.registrar(
                pacienteGuardar.getNombres(),
                "CREAR_USUARIO",
                "Se creó el usuario con username: " + pacienteGuardar.getNombres(),
                "127.0.0.1"  // aquí puedes poner la IP real del request
        );
        RespuestaRs rta = new RespuestaRs();
        rta.setMensaje("Paciente guardado exitosamente");
        rta.setStatus(200);
        return rta;
    }

    private Paciente convertToRqToEntidad(PacienteRq pacienteRq, Usuario usuario) {
        Paciente paciente = new Paciente();
        paciente.setTipoDocumento(pacienteRq.getTipoDocumento());
        paciente.setNumeroDocumento(pacienteRq.getNumeroDocumento());
        paciente.setNombres(pacienteRq.getNombres());
        paciente.setApellidos(pacienteRq.getApellidos());
        paciente.setTelefono(pacienteRq.getTelefono());
        paciente.setGenero(pacienteRq.getTelefono());
        paciente.setFechaNacimiento(pacienteRq.getFechaNacimiento());
        paciente.setDireccion(pacienteRq.getTelefono());
        paciente.setUsuario(usuario);
        return paciente;
    }

    @Override
    public RespuestaRs actualizarPaciente(PacienteRq pacienteRq)
            throws BadRequestException {

        if (pacienteRq.getId() == null) {
            throw new BadRequestException("El id del paciente es obligatorio");
        }

        // 1. Buscar el paciente actual
        Optional<Paciente> optPaciente = pacienteRepository.findById(pacienteRq.getId());
        if (optPaciente.isEmpty()) {
            throw new BadRequestException("El paciente no existe para actualizar");
        }

        Paciente pacienteActual = optPaciente.get();

        // Guardamos valores ANTES para auditoría
        String valoresAntes = pacienteActual.toString();

        // 3. Obtener usuario (por el ID que viene en el DTO)
        Optional<Usuario> optEsp = usuarioRepository.findById(pacienteRq.getUsuario());
        if (optEsp.isEmpty()) {
            throw new BadRequestException("El usuario no existe");
        }
        Usuario usuario = optEsp.get();

        // 4. Actualizar datos
        pacienteActual.setTipoDocumento(pacienteRq.getTipoDocumento());
        pacienteActual.setNumeroDocumento(pacienteRq.getNumeroDocumento());
        pacienteActual.setNombres(pacienteRq.getNombres());
        pacienteActual.setApellidos(pacienteRq.getApellidos());
        pacienteActual.setTelefono(pacienteRq.getTelefono());
        pacienteActual.setGenero(pacienteRq.getGenero());
        pacienteActual.setFechaNacimiento(pacienteRq.getFechaNacimiento());
        pacienteActual.setDireccion(pacienteRq.getDireccion());
        pacienteActual.setUsuario(usuario);

        // 5. Guardar
        Paciente actualizado = pacienteRepository.save(pacienteActual);

        // Valores después de actualización
        String valoresDespues = actualizado.toString();

        // 6. Registrar Auditoría
        auditoriaService.registrar(
                actualizado.getNombres(),
                "ACTUALIZAR_USUARIO",
                "Se actualizó el usuario con ID: " + actualizado.getId(),
                "127.0.0.1"
        );

        // Respuesta
        RespuestaRs rta = new RespuestaRs();
        rta.setMensaje("Paciente actualizado exitosamente");
        rta.setStatus(200);

        return rta;
    }

    @Override
    public RespuestaRs eliminarPaciente(Integer idPaciente) throws BadRequestException {

        Optional<Paciente> optPaciente = pacienteRepository.findById(idPaciente);

        if (optPaciente.isEmpty()) {
            throw new BadRequestException("El paciente no existe, no se puede eliminar");
        }

        Paciente paciente = optPaciente.get();

        // Guardamos valores ANTES para auditoría
        String valoresAntes = paciente.toString();

        // Eliminamos el paciente
        pacienteRepository.delete(paciente);

        // Auditoría
        auditoriaService.registrar(
                paciente.getNombres(),
                "ACTUALIZAR_USUARIO",
                "Se actualizó el usuario con ID: " + paciente.getId(),
                "127.0.0.1"
        );

        // Respuesta
        RespuestaRs rta = new RespuestaRs();
        rta.setMensaje("Paciente eliminado correctamente");
        rta.setStatus(200);

        return rta;
    }

}
