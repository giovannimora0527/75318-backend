/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.uniminuto.clinica.service;

import com.uniminuto.clinica.entity.Cita;
import com.uniminuto.clinica.model.CitaRq;
import com.uniminuto.clinica.model.RespuestaRs;
import org.apache.coyote.BadRequestException;
import com.uniminuto.clinica.model.CitaRs;
import java.util.List;

/**
 *
 * @author Usuario
 */

/**
 * Servicio para la gestión de citas.
 */
public interface CitaService {

    /**
     * Guarda una cita en la base de datos.
     * @param citaRq datos de la cita
     * @return respuesta con mensaje y status
     * @throws BadRequestException si hay datos inválidos
     */
    RespuestaRs guardarCita(CitaRq citaRq) throws BadRequestException;

    /**
     * Lista todas las citas registradas.
     * @return lista de citas
     */
    List<CitaRs> listarCitas();

    /**
     * Lista todas las citas ordenadas de la más reciente a la más antigua.
     * @return lista de citas ordenadas
     */
    List<CitaRs> listarCitasRecientes();
}

