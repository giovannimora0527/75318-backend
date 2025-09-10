package com.uniminuto.clinica.service;

import com.uniminuto.clinica.entity.Paciente;
import java.util.List;
import java.util.Optional;

public interface PacienteService {
    List<Paciente> listarPacientes();

    
    Optional<Paciente> buscarPorDocumento(String numeroDocumento);
}
