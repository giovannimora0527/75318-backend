<<<<<<< HEAD
=======
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
>>>>>>> origin/916724_BrayanEscorcha
package com.uniminuto.clinica.service;

import com.uniminuto.clinica.entity.Paciente;
import java.util.List;
<<<<<<< HEAD

import com.uniminuto.clinica.model.MedicoRq;
import com.uniminuto.clinica.model.PacienteRq;
import com.uniminuto.clinica.model.RespuestaRs;
=======
>>>>>>> origin/916724_BrayanEscorcha
import org.apache.coyote.BadRequestException;

/**
 *
<<<<<<< HEAD
 * @author lmora
 */
public interface PacienteService {
    /**
     * Lista todos los pacientes de la bd.
     * @return Lista de pacientes.
     */
    List<Paciente> encomntrarPorNombre(String nombresPaciente) throws BadRequestException;

    List<Paciente> listaTodosLosPacientes();

    /**
     * Busca un paciente dado un documento de identidad.
     * @param documento documento a buscar.
     * @return Paciente encontrado.
     * @throws BadRequestException excepcion.
     */
    Paciente buscarPacientePorDocumento(String documento) throws BadRequestException;


    List<Paciente> listarOrdenadoPorFechaNacimiento(boolean ascendente);

    RespuestaRs guardarPaciente(PacienteRq pacienteNuevo) throws BadRequestException;


    RespuestaRs actualizarPaciente(PacienteRq pacienteNuevo) throws BadRequestException;
}
=======
 * @author Brayan Escorcha
 */
public interface PacienteService {
    
    List<Paciente> ListarTodosLosPacientes();
    
    Paciente EncontrarPorNombre (String nombrePaciente) throws BadRequestException;
    
}
>>>>>>> origin/916724_BrayanEscorcha
