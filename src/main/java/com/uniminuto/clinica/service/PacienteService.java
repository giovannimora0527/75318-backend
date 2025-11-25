package com.uniminuto.clinica.service;

import com.uniminuto.clinica.entity.Paciente;
import java.util.List;

public interface PacienteService {
    List<Paciente> listarPacientes();
    Paciente encontrarPorNumeroDocumento(String numeroDocumento);
    List<Paciente> listarPacientesPorFechaNacimientoDesc();
    Paciente guardarPaciente(Paciente paciente);
    Paciente actualizarPaciente(Long id, Paciente paciente);
    
}
