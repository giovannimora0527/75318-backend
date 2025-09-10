package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.entity.Paciente;
import com.uniminuto.clinica.repository.PacienteRepository;
import com.uniminuto.clinica.service.PacienteService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class PacienteServiceImpl implements PacienteService {

    private final PacienteRepository pacienteRepository;

    public PacienteServiceImpl(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    @Override
    public List<Paciente> obtenerTodos() {
        return pacienteRepository.findAll();
    }

    @Override
    public Paciente buscarPorDocumento(String documento) throws IllegalArgumentException {
        Paciente paciente = pacienteRepository.buscarPorDocumento(documento);
        if (paciente == null) {
            throw new IllegalArgumentException("No existe paciente con documento: " + documento);
        }
        return paciente;
    }
}
