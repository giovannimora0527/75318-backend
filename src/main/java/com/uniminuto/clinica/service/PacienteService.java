package com.uniminuto.clinica.service;

import com.uniminuto.clinica.entity.Paciente;
import java.util.List;
import org.apache.coyote.BadRequestException;


public interface PacienteService {
   
    List<Paciente> encontrarTodosLosPacientes();

   
    Paciente buscarPacientePorDocumento(String documento) throws BadRequestException;


    List<Paciente> listarOrdenadoPorFechaNacimiento(boolean ascendente);

   
    Paciente guardarOActualizarPaciente(Paciente paciente) throws BadRequestException;
}