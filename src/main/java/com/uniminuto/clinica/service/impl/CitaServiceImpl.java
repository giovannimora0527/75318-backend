package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.entity.Cita;
import com.uniminuto.clinica.entity.Medico;
import com.uniminuto.clinica.entity.Paciente;
import com.uniminuto.clinica.model.CitaRq;
import com.uniminuto.clinica.model.RespuestaRs;
import com.uniminuto.clinica.repository.CitaRepository;
import com.uniminuto.clinica.repository.MedicoRepository;
import com.uniminuto.clinica.repository.PacienteRepository;
import com.uniminuto.clinica.service.CitaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CitaServiceImpl implements CitaService {

    @Autowired
    private final CitaRepository citaRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Override
    public RespuestaRs<Cita> guardarCita(CitaRq citaRq) {
        // 1. Validar si existe el paciente
        Paciente paciente = pacienteRepository.findById(citaRq.getPacienteId())
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));

        // 2. Verificar si existe el medico

        Medico medico = medicoRepository.findById(citaRq.getMedicoId())
                .orElseThrow(() -> new RuntimeException("Médico no encontrado"));

        // 3. Verficar si ya existe la cita
        Optional<Cita> citaExistente = citaRepository.findByFechaHoraAndMedico(citaRq.getFechaHora(), medico);
        if (citaExistente.isPresent()) {
            return new RespuestaRs<>("Ya existe una cita con ese médico en esa fecha y hora", 400, null);
        }
        // 2. Crar ka cita
        Cita cita = new Cita();
        cita.setPaciente(paciente);
        cita.setMedico(medico);
        cita.setFechaHora(citaRq.getFechaHora());
        cita.setEstado(citaRq.getEstado());
        cita.setMotivo(citaRq.getMotivo());

        // 3. Guardar la cita
        Cita guardada = citaRepository.save(cita);

        // 4. Respuesta exitosa
        return new RespuestaRs<>("Cita guardada correctamente", 201, guardada);
    }

    @Override
    public List<Cita> listarCitas() {
        return citaRepository.findAll();
    }
    @Override
    public List<Cita> listarCitasOrdenadosPorFecha() {
        return this.citaRepository.findAllByOrderByFechaHoraDesc();
    }
}
