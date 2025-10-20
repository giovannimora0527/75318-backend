/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.entity.Paciente;
import com.uniminuto.clinica.model.PacienteRq;
import com.uniminuto.clinica.model.PacienteRs;
import com.uniminuto.clinica.model.RespuestaRs;
import com.uniminuto.clinica.repository.PacienteRepository;
import com.uniminuto.clinica.service.PacienteService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
/**
 * 
 * @author Usuario
 */

@Service
public class PacienteServiceImpl implements PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Override
    public RespuestaRs guardarPaciente(PacienteRq pacienteRq) throws BadRequestException {
        validarPacienteRq(pacienteRq);

        boolean esActualizacion = pacienteRq.getId() != null;

        Paciente paciente = esActualizacion
        ? pacienteRepository.findById(pacienteRq.getId()).orElse(new Paciente())
        : new Paciente();

          // Mapear campos desde el RQ a la entidad
          paciente.setTipoDocumento(pacienteRq.getTipoDocumento());
          paciente.setNumeroDocumento(pacienteRq.getNumeroDocumento());
          paciente.setNombres(pacienteRq.getNombres());
          paciente.setApellidos(pacienteRq.getApellidos());
          paciente.setFechaNacimiento(pacienteRq.getFechaNacimiento());
          paciente.setGenero(pacienteRq.getGenero());
          paciente.setTelefono(pacienteRq.getTelefono());
          paciente.setDireccion(pacienteRq.getDireccion());

         // Validar duplicados solo si es creación o cambio de documento
         Optional<Paciente> existente = pacienteRepository.findByNumeroDocumento(pacienteRq.getNumeroDocumento());
           if (existente.isPresent() && (!esActualizacion || !existente.get().getId().equals(pacienteRq.getId()))) {
            throw new BadRequestException("Ya existe un paciente con el número de documento: " + pacienteRq.getNumeroDocumento());
        }

        pacienteRepository.save(paciente);

        RespuestaRs respuesta = new RespuestaRs();
        respuesta.setStatus(200);
        respuesta.setMensaje(esActualizacion ? "Paciente actualizado con éxito" : "Paciente guardado con éxito");
        respuesta.setStatus(200);
        return respuesta;
    }

    private void validarPacienteRq(PacienteRq pacienteRq) throws BadRequestException {
        if (pacienteRq == null) {
            throw new BadRequestException("El cuerpo de la petición no puede ser vacío");
        }
        if (pacienteRq.getTipoDocumento() == null || pacienteRq.getTipoDocumento().trim().isEmpty()) {
            throw new BadRequestException("El campo tipoDocumento es obligatorio");
        }
        if (pacienteRq.getNumeroDocumento() == null || pacienteRq.getNumeroDocumento().trim().isEmpty()) {
            throw new BadRequestException("El campo numeroDocumento es obligatorio");
        }
        if (pacienteRq.getNombres() == null || pacienteRq.getNombres().trim().isEmpty()) {
            throw new BadRequestException("El campo nombres es obligatorio");
        }
        if (pacienteRq.getApellidos() == null || pacienteRq.getApellidos().trim().isEmpty()) {
            throw new BadRequestException("El campo apellidos es obligatorio");
        }
        if (pacienteRq.getFechaNacimiento() == null) {
            throw new BadRequestException("El campo fechaNacimiento es obligatorio");
        }
    }

    @Override
    public List<PacienteRs> listarPacientes() {
        return pacienteRepository.findAll().stream().map(p -> {
            PacienteRs dto = new PacienteRs();
            dto.setId(p.getId());
            dto.setTipoDocumento(p.getTipoDocumento());
            dto.setNumeroDocumento(p.getNumeroDocumento());
            dto.setNombres(p.getNombres());
            dto.setApellidos(p.getApellidos());
            dto.setFechaNacimiento(p.getFechaNacimiento());
            dto.setGenero(p.getGenero());
            dto.setTelefono(p.getTelefono());
            dto.setDireccion(p.getDireccion());
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public List<PacienteRs> listarPacientesRecientes() {
        return pacienteRepository.findAllByOrderByIdDesc().stream().map(p -> {
            PacienteRs dto = new PacienteRs();
            dto.setId(p.getId());
            dto.setTipoDocumento(p.getTipoDocumento());
            dto.setNumeroDocumento(p.getNumeroDocumento());
            dto.setNombres(p.getNombres());
            dto.setApellidos(p.getApellidos());
            dto.setFechaNacimiento(p.getFechaNacimiento());
            dto.setGenero(p.getGenero());
            dto.setTelefono(p.getTelefono());
            dto.setDireccion(p.getDireccion());
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public List<PacienteRs> listarPacientesPorFechaNacimiento() {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
}
