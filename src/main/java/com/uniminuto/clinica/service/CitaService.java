/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.uniminuto.clinica.service;

import com.uniminuto.clinica.model.CitaRq;
import com.uniminuto.clinica.model.CitaRs;
import com.uniminuto.clinica.model.RespuestaRs;
import java.util.List;
import org.apache.coyote.BadRequestException;

/**
 * @author Usuario
 */
public interface CitaService {

    /**
     * Obtiene todas las citas
     */
    List<CitaRs> listarCitas();
     
    /**
     * Obtiene las citas más recientes
     */
    List<CitaRs> listarCitasRecientes();
     
    /**
     * Guarda o actualiza una cita
     */
    RespuestaRs guardarCita(CitaRq citaRq) throws BadRequestException;
    
    /**
     * Obtiene citas por paciente
     */
    List<CitaRs> listarCitasPorPaciente(Long pacienteId);
    
    /**
     * Obtiene citas por médico
     */
    List<CitaRs> listarCitasPorMedico(Long medicoId);
    
    /**
     * Obtiene citas por estado
     */
    List<CitaRs> listarCitasPorEstado(String estado);
}