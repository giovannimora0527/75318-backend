package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.entity.Cita;
import com.uniminuto.clinica.repository.CitaRepository;
import com.uniminuto.clinica.service.CitaService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * @author JAVIER-CUERVO
 */
@Service
public class CitaServiceImpl implements CitaService {

    private final CitaRepository citaRepository;

    public CitaServiceImpl(CitaRepository citaRepository) {
        this.citaRepository = citaRepository;
    }

    @Override
    public Cita guardarCita(Cita cita) {
        return citaRepository.save(cita);
    }

    @Override
    public List<Cita> listarCitas() {
        return citaRepository.findAll();
    }

    @Override
    public List<Cita> listarPorPaciente(Long pacienteId) {
        return citaRepository.findByPacienteId(pacienteId);
    }

    @Override
    public List<Cita> listarPorMedico(Long medicoId) {
        return citaRepository.findByMedicoId(medicoId);
    }

    @Override
    public List<Cita> listarPorFechaHora() {
        return this.citaRepository.findAllByOrderByFechaHoraDesc();
    }
}