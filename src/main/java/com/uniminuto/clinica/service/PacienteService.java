package com.uniminuto.clinica.service;

import com.uniminuto.clinica.entity.Paciente;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * @author jartunduaga
 */
@Service
public interface PacienteService {
    List<Paciente> listarLosPacientes();
}
