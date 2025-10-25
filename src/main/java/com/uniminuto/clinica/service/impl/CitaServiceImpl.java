/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.entity.Cita;
import com.uniminuto.clinica.entity.Paciente;
import com.uniminuto.clinica.entity.Medico;
import com.uniminuto.clinica.model.CitaRq;
import com.uniminuto.clinica.model.CitaRs;
import com.uniminuto.clinica.model.RespuestaRs;
import com.uniminuto.clinica.repository.CitaRepository;
import com.uniminuto.clinica.repository.PacienteRepository;
import com.uniminuto.clinica.repository.MedicoRepository;
import com.uniminuto.clinica.service.CitaService;
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
public class CitaServiceImpl implements CitaService {

    @Autowired
    private CitaRepository citaRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Override
    public RespuestaRs guardarCita(CitaRq citaRq) throws BadRequestException {
        validarCitaRq(citaRq);

        Optional<Paciente> pacienteOpt = pacienteRepository.findById(citaRq.getPacienteId());
        if (!pacienteOpt.isPresent()) {
            throw new BadRequestException("Paciente no encontrado con id: " + citaRq.getPacienteId());
        }

        Optional<Medico> medicoOpt = medicoRepository.findById(citaRq.getMedicoId());
        if (!medicoOpt.isPresent()) {
            throw new BadRequestException("Médico no encontrado con id: " + citaRq.getMedicoId());
        }

        boolean esActualizacion = citaRq.getId() != null;
        Cita cita = esActualizacion
            ? citaRepository.findById(citaRq.getId()).orElse(new Cita())
            : new Cita();

        // Mapear campos desde el RQ a la entidad
        cita.setPaciente(pacienteOpt.get());
        cita.setMedico(medicoOpt.get());
        cita.setFechaHora(citaRq.getFechaHora());
        cita.setEstado(citaRq.getEstado());
        cita.setMotivo(citaRq.getMotivo());

        // Validar duplicados solo si es creación
        if (!esActualizacion && citaRepository.existsByMedicoAndPacienteAndFechaHora(
                cita.getMedico(), cita.getPaciente(), cita.getFechaHora())) {
            throw new BadRequestException("Ya existe una cita para ese médico, paciente y fecha/hora");
        }

        citaRepository.save(cita);

        RespuestaRs respuesta = new RespuestaRs();
        respuesta.setStatus(200);
        respuesta.setMensaje(esActualizacion ? "Cita actualizada con éxito" : "Cita guardada con éxito");
        return respuesta;
    }

    private void validarCitaRq(CitaRq citaRq) throws BadRequestException {
        if (citaRq == null) {
            throw new BadRequestException("El cuerpo de la petición no puede ser vacío");
        }
        if (citaRq.getPacienteId() == null) {
            throw new BadRequestException("El campo pacienteId es obligatorio");
        }
        if (citaRq.getMedicoId() == null) {
            throw new BadRequestException("El campo medicoId es obligatorio");
        }
        if (citaRq.getFechaHora() == null) {
            throw new BadRequestException("El campo fechaHora es obligatorio");
        }
        if (citaRq.getEstado() == null || citaRq.getEstado().trim().isEmpty()) {
            throw new BadRequestException("El campo estado es obligatorio");
        }
    }

    @Override
    public List<CitaRs> listarCitas() {
        return citaRepository.findAllByOrderByFechaHoraDesc().stream().map(c -> {
            CitaRs dto = new CitaRs();
            dto.setId(c.getId());
            dto.setFechaHora(c.getFechaHora().toString());
            dto.setEstado(c.getEstado());
            dto.setMotivo(c.getMotivo());
            dto.setNombrePaciente(c.getPaciente().getNombres() + " " + c.getPaciente().getApellidos());
            dto.setNombreMedico(c.getMedico().getNombres() + " " + c.getMedico().getApellidos());
            dto.setPacienteId(c.getPaciente().getId());
            dto.setMedicoId(c.getMedico().getId());

            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public List<CitaRs> listarCitasRecientes() {
        return citaRepository.findAllByOrderByFechaHoraDesc().stream()
            .limit(10)
            .map(c -> {
                CitaRs dto = new CitaRs();
                dto.setId(c.getId());
                dto.setFechaHora(c.getFechaHora().toString());
                dto.setEstado(c.getEstado());
                dto.setMotivo(c.getMotivo());
                dto.setNombrePaciente(c.getPaciente().getNombres() + " " + c.getPaciente().getApellidos());
                dto.setNombreMedico(c.getMedico().getNombres() + " " + c.getMedico().getApellidos());
                dto.setPacienteId(c.getPaciente().getId());
                dto.setMedicoId(c.getMedico().getId());

                return dto;
            }).collect(Collectors.toList());
    }

    @Override
    public List<CitaRs> listarCitasPorPaciente(Long pacienteId) {
        return citaRepository.findByPacienteId(pacienteId).stream().map(c -> {
            CitaRs dto = new CitaRs();
            dto.setId(c.getId());
            dto.setFechaHora(c.getFechaHora().toString());
            dto.setEstado(c.getEstado());
            dto.setMotivo(c.getMotivo());
            dto.setNombrePaciente(c.getPaciente().getNombres() + " " + c.getPaciente().getApellidos());
            dto.setNombreMedico(c.getMedico().getNombres() + " " + c.getMedico().getApellidos());
            dto.setPacienteId(c.getPaciente().getId());
            dto.setMedicoId(c.getMedico().getId());

            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public List<CitaRs> listarCitasPorMedico(Long medicoId) {
        return citaRepository.findByMedicoId(medicoId).stream().map(c -> {
            CitaRs dto = new CitaRs();
            dto.setId(c.getId());
            dto.setFechaHora(c.getFechaHora().toString());
            dto.setEstado(c.getEstado());
            dto.setMotivo(c.getMotivo());
            dto.setNombrePaciente(c.getPaciente().getNombres() + " " + c.getPaciente().getApellidos());
            dto.setNombreMedico(c.getMedico().getNombres() + " " + c.getMedico().getApellidos());
            dto.setPacienteId(c.getPaciente().getId());
            dto.setMedicoId(c.getMedico().getId());

            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public List<CitaRs> listarCitasPorEstado(String estado) {
        return citaRepository.findByEstado(estado).stream().map(c -> {
            CitaRs dto = new CitaRs();
            dto.setId(c.getId());
            dto.setFechaHora(c.getFechaHora().toString());
            dto.setEstado(c.getEstado());
            dto.setMotivo(c.getMotivo());
            dto.setNombrePaciente(c.getPaciente().getNombres() + " " + c.getPaciente().getApellidos());
            dto.setNombreMedico(c.getMedico().getNombres() + " " + c.getMedico().getApellidos());
            dto.setPacienteId(c.getPaciente().getId());
            dto.setMedicoId(c.getMedico().getId());

            return dto;
        }).collect(Collectors.toList());
    }
}