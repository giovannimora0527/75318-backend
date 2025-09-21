package com.uniminuto.clinica.service;

import com.uniminuto.clinica.entity.Paciente;
import java.util.List;
import org.apache.coyote.BadRequestException;

/**
 *
 * @author 1079977_JavierCuervo
 */
public interface PacienteService {

    List<Paciente> listarTodosLosPacientes();

    Paciente encontrarPorFechaNacimiento(String fecha_nacimiento) throws BadRequestException;

    List<Paciente> listarPorFechaNacimiento();
}
