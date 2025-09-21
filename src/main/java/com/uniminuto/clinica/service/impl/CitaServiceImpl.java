package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.entity.Cita;
import com.uniminuto.clinica.entity.Medico;
import com.uniminuto.clinica.entity.Paciente;
import com.uniminuto.clinica.model.CitaRq;
import com.uniminuto.clinica.repository.CitaRepository;
import com.uniminuto.clinica.repository.MedicoRepository;
import com.uniminuto.clinica.repository.PacienteRepository;
import com.uniminuto.clinica.service.CitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CitaServiceImpl implements CitaService {

    @Autowired
    private CitaRepository citaRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Override
    public Cita guardarCita(CitaRq citaRq) {
        validarCampos(citaRq);

        Paciente paciente = pacienteRepository.findById(citaRq.getPacienteId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "El paciente no fue encontrado con el ID: " + citaRq.getPacienteId()
                ));

        Medico medico = medicoRepository.findById(citaRq.getMedicoId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "El médico no fue encontrado con el ID: " + citaRq.getMedicoId()
                ));

        Cita cita = new Cita();
        cita.setPaciente(paciente);
        cita.setMedico(medico);
        cita.setFechaHora(citaRq.getFechaHora());
        cita.setMotivo(citaRq.getMotivo());
        cita.setEstado("PENDIENTE"); // Valor por defecto

        return citaRepository.save(cita);
    }

    @Override
    public List<Cita> listarCitaPorFecha(String orden) {
        Sort.Direction direccion = "asc".equalsIgnoreCase(orden)
                ? Sort.Direction.ASC
                : Sort.Direction.DESC;
        return citaRepository.findAll(Sort.by(direccion, "fechaHora"));
    }

    private void validarCampos(CitaRq citaRq) {
        if (citaRq.getPacienteId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El campo pacienteId es obligatorio.");
        }
        if (citaRq.getMedicoId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El campo medicoId es obligatorio.");
        }
        if (citaRq.getFechaHora() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El campo fechaHora es obligatorio.");
        }
        if (citaRq.getMotivo() == null || citaRq.getMotivo().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El campo motivo es obligatorio.");
        }
    }
}
