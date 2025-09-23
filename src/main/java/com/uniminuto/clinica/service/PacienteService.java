/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.uniminuto.clinica.service;

import com.uniminuto.clinica.entity.Paciente;
import java.util.List;

/**
 * @author Usuario
 */
public interface PacienteService {

    /**
     * Obtiene todos los pacientes ordenados por fecha de nacimiento 
     */
    List<Paciente> listarPacientesPorFechaNacimiento();
    
     List<Paciente> listarPacientes();
}
