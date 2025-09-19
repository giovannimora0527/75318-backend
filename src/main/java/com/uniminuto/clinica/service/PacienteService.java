package com.uniminuto.clinica.service;

import com.uniminuto.clinica.entity.Paciente;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * @author jartunduaga
 */
@Service
public interface PacienteService {
    List<Paciente> listarLosPacientes();
    List<Paciente> listarPacientePorFechaNacimiento(String orden);
    Paciente encontrarPorDocumento(String numeroDocumento) throws BadRequestException;


}
