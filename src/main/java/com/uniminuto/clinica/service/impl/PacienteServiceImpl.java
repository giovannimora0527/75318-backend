package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.entity.*;
import com.uniminuto.clinica.model.PacienteRq;
import com.uniminuto.clinica.model.RespuestaRs;
import com.uniminuto.clinica.repository.PacienteRepository;
import com.uniminuto.clinica.repository.UsuarioRepository;
import com.uniminuto.clinica.service.PacienteService;

import java.util.List;
import java.util.Optional;

import com.uniminuto.clinica.service.UsuarioService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author lmora
 */
@Service
public class PacienteServiceImpl implements PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private UsuarioService usuarioService;


    @Autowired
    private UsuarioRepository usuarioRepository;

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
        this.pacienteRepository.save(pacienteGuardar);
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
        // Paso 1. Consultar si el campo id existe y viene en el request
        if (pacienteRq.getId() == null) {
            throw new BadRequestException("El id del paciente es obligatorio");
        }
        // Paso 2. Consultar si el medicamento existe por id
        Optional<Paciente> optPaciente = pacienteRepository
                .findById(pacienteRq.getId());
        // Paso 3. Si no existe lanzo error
        if (!optPaciente.isPresent()) {
            throw new BadRequestException("El paciente no existe y no se puede actualizar");
        }
        // Paso 4. Si existe voy y valido que el atributo nombre cambie y si cambia lo consulto por nombre
        Paciente pacienteActual = optPaciente.get();
        if (!pacienteActual.getNombres()
                .toLowerCase().equals(pacienteRq.getNombres().toLowerCase())) {
            Optional<Paciente> optPacientePorNombre = pacienteRepository
                    .findByNombres(pacienteRq.getNombres());
            // Paso 5. Si existe por nombre lanzo error
            if (optPacientePorNombre.isPresent()) {
                throw new BadRequestException("El nombre del paciente ya existe");
            }
        }

        // Paso 6. Si no existe por nombre, actualizo los datos del medicamento
        pacienteActual.setTipoDocumento(pacienteRq.getTipoDocumento() == null ? pacienteActual.getTipoDocumento() : pacienteRq.getTipoDocumento());
        pacienteActual.setNumeroDocumento(pacienteRq.getNumeroDocumento() == null ? pacienteActual.getNumeroDocumento() : pacienteRq.getNumeroDocumento());
        pacienteActual.setNombres(pacienteRq.getNombres() == null ? pacienteActual.getNombres() : pacienteRq.getNombres());
        pacienteActual.setApellidos(pacienteRq.getApellidos() == null ? pacienteActual.getApellidos() : pacienteRq.getApellidos());
        pacienteActual.setTelefono(pacienteRq.getTelefono() == null ? pacienteActual.getTelefono() : pacienteRq.getTelefono());
        pacienteActual.setGenero(pacienteRq.getGenero() == null ? pacienteActual.getGenero() : pacienteRq.getGenero());
        pacienteActual.setFechaNacimiento(pacienteRq.getFechaNacimiento() == null ? pacienteActual.getFechaNacimiento() : pacienteRq.getFechaNacimiento());
        pacienteActual.setDireccion(pacienteRq.getDireccion() == null ? pacienteActual.getDireccion() : pacienteRq.getDireccion());

        this.pacienteRepository.save(pacienteActual);
        // Paso 7. Retorno la respuesta
        RespuestaRs rta = new RespuestaRs();
        rta.setMensaje("Paciente actualizado exitosamente");
        rta.setStatus(200);

        return rta;
    }

}
