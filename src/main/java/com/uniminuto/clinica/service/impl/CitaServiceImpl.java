package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.entity.Cita;
import com.uniminuto.clinica.model.CitaRespDTO;
import com.uniminuto.clinica.repository.CitaRepository;
import com.uniminuto.clinica.repository.PacienteRepository;
import com.uniminuto.clinica.repository.MedicoRepository;
import com.uniminuto.clinica.service.CitaService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CitaServiceImpl implements CitaService {

    private final CitaRepository citaRepository;
    private final PacienteRepository pacienteRepository;
    private final MedicoRepository medicoRepository;

    public CitaServiceImpl(CitaRepository citaRepository,
                           PacienteRepository pacienteRepository,
                           MedicoRepository medicoRepository) {
        this.citaRepository = citaRepository;
        this.pacienteRepository = pacienteRepository;
        this.medicoRepository = medicoRepository;
    }

    @Override
    public Cita guardarCita(Cita cita) {
        return citaRepository.save(cita);
    }

    @Override
    public List<Cita> obtenerTodas() {
        return citaRepository.findAll();
    }

    @Override
    public Cita obtenerPorId(Long id) {
        return citaRepository.findById(id).orElse(null);
    }

    @Override
    public List<Cita> listarCitasPorFechaHoraDesc() {
        return citaRepository.findAllByOrderByFechaHoraDesc();
    }

    // Nuevo método para devolver DTO con nombres
    public List<CitaRespDTO> listarCitasConNombres() {
        return citaRepository.findAll().stream().map(cita -> {
            String pacienteNombre = pacienteRepository.findById(cita.getPacienteId())
                    .map(p -> p.getNombres() + " " + p.getApellidos())
                    .orElse("Paciente no encontrado");

            String medicoNombre = medicoRepository.findById(cita.getMedicoId())
                    .map(m -> m.getNombres() + " " + m.getApellidos())
                    .orElse("Médico no encontrado");

            return new CitaRespDTO(
                    cita.getId(),
                    pacienteNombre,
                    medicoNombre,
                    cita.getMotivo(),
                    cita.getFechaHora(),
                    cita.getEstado()
            );
        }).collect(Collectors.toList());
    }
}
