package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.entity.*;
import com.uniminuto.clinica.model.HistoriaRq;
import com.uniminuto.clinica.model.RespuestaRs;
import com.uniminuto.clinica.repository.HistoriaRepository;
import com.uniminuto.clinica.repository.PacienteRepository;
import com.uniminuto.clinica.service.HistoriaService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoriaServiceImpl implements HistoriaService {

    /**
     * Repositorio de datos para citas.
     */
    @Autowired
    private HistoriaRepository historiaRepository;

    /**
     * Repositorio de datos para pacientes.
     */
    @Autowired
    private PacienteRepository pacienteRepository;

    @Override
    public List<Historia> listarTodasLasHistorias() {
        return historiaRepository.findAll();
    }

    @Override
    public RespuestaRs guardarHistoria(HistoriaRq historiaRq) throws BadRequestException {
        if (historiaRq.getPaciente() == null) {
            throw new BadRequestException("Debe especificar un paciente válido.");
        }

        Paciente paciente = pacienteRepository.findById(historiaRq.getPaciente())
                .orElseThrow(() -> new BadRequestException("Paciente no encontrado"));

        Historia nuevo = new Historia();
        nuevo.setPaciente(paciente);
        nuevo.setDescripcion(historiaRq.getDescripcion());
        nuevo.setFecha(historiaRq.getFecha());

        this.historiaRepository.save(nuevo);
        // Paso 5. Devuelve respuesta ok
        RespuestaRs rta = new RespuestaRs();
        rta.setMensaje("La historia se ha guardado correctamente.");
        rta.setStatus(200);
        return rta;
    }


    @Override
    public RespuestaRs actualizarHistoria(HistoriaRq historiaRq) throws BadRequestException {
        if (historiaRq.getId() == null) {
            throw new BadRequestException("El id de la historia es obligatorio para actualizar.");
        }

        Historia historiaExistente = historiaRepository.findById(historiaRq.getId())
                .orElseThrow(() -> new BadRequestException("Historia no encontrada"));

        if (historiaRq.getPaciente() != null) {
            Paciente paciente = pacienteRepository.findById(historiaRq.getPaciente())
                    .orElseThrow(() -> new BadRequestException("Paciente no encontrado"));
            historiaExistente.setPaciente(paciente);
        }

        if (historiaRq.getDescripcion() != null) {
            historiaExistente.setDescripcion(historiaRq.getDescripcion());
        }

        if (historiaRq.getFecha() != null) {
            historiaExistente.setFecha(historiaRq.getFecha());
        }

        this.historiaRepository.save(historiaExistente);

        RespuestaRs rta = new RespuestaRs();
        rta.setMensaje("La historia se ha actualizado correctamente.");
        rta.setStatus(200);
        return rta;
    }
}