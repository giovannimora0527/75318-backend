package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.entity.*;
import com.uniminuto.clinica.model.CitaRq;
import com.uniminuto.clinica.model.RespuestaRs;
import com.uniminuto.clinica.repository.CitaRepository;
import com.uniminuto.clinica.repository.MedicoRepository;
import com.uniminuto.clinica.repository.PacienteRepository;
import com.uniminuto.clinica.service.CitaService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CitaServiceImpl implements CitaService {

    /**
     * Repositorio de datos para citas.
     */
    @Autowired
    private CitaRepository citaRepository;

    /**
     * Repositorio de datos para pacientes.
     */
    @Autowired
    private PacienteRepository pacienteRepository;

    /**
     * Repositorio de datos para médicos.
     */
    @Autowired
    private MedicoRepository medicoRepository;

    @Override
    public List<Cita> listarCitas() {
        return citaRepository.findAllByOrderByFechaHoraDesc();
    }

    @Override
    public RespuestaRs guardarCita(CitaRq citaRq) throws BadRequestException {
        Optional<Cita> optCita = this.citaRepository.findById(
                citaRq.getId()
        );
        if (optCita.isPresent()) {
            throw new BadRequestException("El id ya está registrado");
        }

        Optional<Paciente> optPac = this.pacienteRepository
                .findById(citaRq.getPaciente());
        if (optPac.isEmpty()) {
            throw new BadRequestException("El paciente no existe");
        }

        Optional<Medico> optMed = this.medicoRepository
                .findById(citaRq.getMedico());
        if (optMed.isEmpty()) {
            throw new BadRequestException("El medico no existe");
        }

        Cita citaGuardar = this.convertToRqToEntidad(citaRq, optMed.get(), optPac.get());
        this.citaRepository.save(citaGuardar);
        RespuestaRs rta = new RespuestaRs();
        rta.setMensaje("Cita guardada exitosamente");
        rta.setStatus(200);
        return rta;
    }

    private Cita convertToRqToEntidad(CitaRq citaRq, Medico medico, Paciente paciente) {
        Cita cita = new Cita();
        cita.setPaciente(paciente);
        cita.setMedico(medico);
        cita.setFechaHora(LocalDateTime.parse(citaRq.getFechaHora()));
        cita.setEstado(citaRq.getEstado());
        cita.setMotivo(citaRq.getMotivo());
        return cita;
    }


    public RespuestaRs actualizarCita(CitaRq citaRq)
            throws BadRequestException {
        // Paso 1. Consultar si el campo id existe y viene en el request
        if (citaRq.getId() == null) {
            throw new BadRequestException("El id de la cita es obligatoria");
        }
        // Paso 2. Consultar si el medicamento existe por id
        Optional<Cita> optCita = citaRepository
                .findById(citaRq.getId());
        // Paso 3. Si no existe lanzo error
        if (!optCita.isPresent()) {
            throw new BadRequestException("La cita no existe y no se puede actualizar");
        }
        // Paso 4. Si existe voy y valido que el atributo nombre cambie y si cambia lo consulto por nombre
        Cita citaActual = optCita.get();
        if (!citaActual.getId().equals(citaRq.getId())) {}
            Optional<Cita> optCitaPorId = citaRepository
                    .findById(citaRq.getId());
            // Paso 5. Si existe por nombre lanzo error
            if (optCitaPorId.isPresent()) {
                throw new BadRequestException("El id de la cita ya existe");
            }


        // Paso 6. Si no existe por nombre, actualizo los datos del medicamento
        citaActual.setFechaHora(citaRq.getFechaHora() == null ? citaActual.getFechaHora() : LocalDateTime.parse(citaRq.getFechaHora()));
        citaActual.setEstado(citaRq.getEstado() == null ? citaActual.getEstado() : citaRq.getEstado());
        citaActual.setMotivo(citaRq.getMotivo() == null ? citaActual.getMotivo() : citaRq.getMotivo());
        this.citaRepository.save(citaActual);
        // Paso 7. Retorno la respuesta
        RespuestaRs rta = new RespuestaRs();
        rta.setMensaje("Medicamento actualizado exitosamente");
        rta.setStatus(200);

        return rta;
    }

    }

