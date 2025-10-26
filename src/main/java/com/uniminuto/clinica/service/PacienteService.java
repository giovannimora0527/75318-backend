package com.uniminuto.clinica.service;

import com.uniminuto.clinica.entity.Paciente;
import java.util.List;

import com.uniminuto.clinica.model.PacienteRq;
import com.uniminuto.clinica.model.RespuestaRs;
import org.apache.coyote.BadRequestException;

/**
 *
 * @author lmora
 */
public interface PacienteService {
    /**
     * Lista todos los pacientes de la bd.
     * @return Lista de pacientes.
     */
    List<Paciente> encontrarTodosLosPacientes();

    Paciente buscarPacientePorDocumento(String documento) throws BadRequestException;

    List<Paciente> listarOrdenadoPorNombres(boolean ascendente);

    RespuestaRs guardarPaciente(PacienteRq pacienteRq) throws BadRequestException;

    RespuestaRs actualizarPaciente(PacienteRq pacienteRq) throws BadRequestException;
}