/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.uniminuto.clinica.service;

import com.uniminuto.clinica.model.RecetaRq;
import com.uniminuto.clinica.model.RecetaRs;
import com.uniminuto.clinica.model.RespuestaRs;
import java.util.List;
import org.apache.coyote.BadRequestException;

/**
 * @author Usuario
 */
public interface RecetaService {

    /**
     * Obtiene todas las recetas ordenadas por fecha de creación
     */
    List<RecetaRs> listarRecetas();
     
    /**
     * Obtiene las recetas más recientes
     */
    List<RecetaRs> listarRecetasRecientes();
     
    /**
     * Guarda o actualiza una receta
     */
    RespuestaRs guardarReceta(RecetaRq recetaRq) throws BadRequestException;
    
    /**
     * Obtiene recetas por cita ID
     */
    List<RecetaRs> listarRecetasPorCita(Long citaId);
    
    /**
     * Obtiene recetas por medicamento ID
     */
    List<RecetaRs> listarRecetasPorMedicamento(Long medicamentoId);
}