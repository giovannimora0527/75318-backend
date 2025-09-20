package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.entity.Cita;
import com.uniminuto.clinica.entity.Medico;
import com.uniminuto.clinica.entity.Paciente;
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

    public Cita guardarCita(Long pacienteId, Long medicoId, Cita cita){
        Optional<Paciente> pacienteOpcional = pacienteRepository.findById(pacienteId);
        if (!pacienteOpcional.isPresent()){
            throw new RuntimeException("El Paciente no fue encontrado con el ID: "+ pacienteId);
        }
        Optional<Medico> medicoOpcional = medicoRepository.findById(medicoId);
        if (!medicoOpcional.isPresent()){
            throw new RuntimeException("El medico no fue encontrado con el ID: "+ medicoId);
        }
        cita.setPaciente(pacienteOpcional.get());
        cita.setMedico(medicoOpcional.get());

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
