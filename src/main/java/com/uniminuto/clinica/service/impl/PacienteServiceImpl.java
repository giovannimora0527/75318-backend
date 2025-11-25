package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.entity.Paciente;
import com.uniminuto.clinica.repository.PacienteRepository;
import com.uniminuto.clinica.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PacienteServiceImpl implements PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Override
    public List<Paciente> listarPacientes() {
        return pacienteRepository.findAll();
    }

    @Override
    public Paciente encontrarPorNumeroDocumento(String numeroDocumento) {
        return pacienteRepository.findByNumeroDocumento(numeroDocumento)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));
    }

    @Override
    public List<Paciente> listarPacientesPorFechaNacimientoDesc() {
        return pacienteRepository.findAllByOrderByFechaNacimientoDesc();
    }

    @Override
    public Paciente guardarPaciente(Paciente paciente) {
        return pacienteRepository.save(paciente);
    }

    @Override
    public Paciente actualizarPaciente(Long id, Paciente paciente) {
        Paciente p = pacienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));
        // Actualizamos los campos
        p.setNombres(paciente.getNombres());
        p.setApellidos(paciente.getApellidos());
        p.setTipoDocumento(paciente.getTipoDocumento());
        p.setNumeroDocumento(paciente.getNumeroDocumento());
        p.setFechaNacimiento(paciente.getFechaNacimiento());
        p.setGenero(paciente.getGenero());
        p.setTelefono(paciente.getTelefono());
        p.setDireccion(paciente.getDireccion());
        p.setEdad(paciente.getEdad());
        return pacienteRepository.save(p);
    }
}

