/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.uniminuto.clinica.service;

import com.uniminuto.clinica.entity.Paciente;
import com.uniminuto.clinica.model.PacienteRq;
import com.uniminuto.clinica.model.PacienteRs;
import com.uniminuto.clinica.model.RespuestaRs;
import java.util.List;
import org.apache.coyote.BadRequestException;

/**
 * @author Usuario
 */
public interface PacienteService {

    /**
     * Obtiene todos los pacientes ordenados por fecha de nacimiento 
     */
    List<PacienteRs> listarPacientesPorFechaNacimiento();
    
    List<PacienteRs> listarPacientes();
     
    List<PacienteRs> listarPacientesRecientes();
     
    RespuestaRs guardarPaciente(PacienteRq pacienteRq) throws BadRequestException;
}
