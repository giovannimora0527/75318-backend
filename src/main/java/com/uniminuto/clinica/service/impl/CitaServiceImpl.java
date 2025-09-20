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
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CitaServiceImpl implements CitaService {
    @Autowired
    private CitaRepository citaRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    public Cita guardarCita(CitaRq citaRq) {
        Optional<Paciente> pacienteOpcional = pacienteRepository.findById(citaRq.getPacienteId());
        if (!pacienteOpcional.isPresent()) {
            throw new RuntimeException("El paciente no fue encontrado con el ID: " + citaRq.getPacienteId());
        }

        Optional<Medico> medicoOpcional = medicoRepository.findById(citaRq.getMedicoId());
        if (!medicoOpcional.isPresent()) {
            throw new RuntimeException("El médico no fue encontrado con el ID: " + citaRq.getMedicoId());
        }

        Cita cita = new Cita();
        cita.setPaciente(pacienteOpcional.get());
        cita.setMedico(medicoOpcional.get());
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
}
