/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.uniminuto.clinica.api;

import com.uniminuto.clinica.model.CitaRq;
import com.uniminuto.clinica.model.CitaRs;
import com.uniminuto.clinica.model.RespuestaRs;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 
 * @author Usuario
 */

@CrossOrigin(origins = "*")
@RequestMapping("/cita")
public interface CitaApi {

    @RequestMapping(
            value = "/listar",
            produces = {"application/json"},
            method = RequestMethod.GET)
    List<CitaRs> listarCitas();

    @RequestMapping(
            value = "/recientes",
            produces = {"application/json"},
            method = RequestMethod.GET)
    List<CitaRs> listarCitasRecientes();

    @RequestMapping(
            value = "/guardar",
            consumes = {"application/json"},
            produces = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<RespuestaRs> guardarCita(@RequestBody CitaRq citaRq) throws BadRequestException;
    
    @RequestMapping(
        value = "/actualizar",
        consumes = {"application/json"},
        produces = {"application/json"},
        method = RequestMethod.POST)
    ResponseEntity<RespuestaRs> actualizarCita(@RequestBody CitaRq citaRq) throws BadRequestException;
    
    @RequestMapping(
        value = "/por-paciente",
        produces = {"application/json"},
        method = RequestMethod.GET)
    List<CitaRs> listarCitasPorPaciente(@RequestParam Long pacienteId);
    
    @RequestMapping(
        value = "/por-medico",
        produces = {"application/json"},
        method = RequestMethod.GET)
    List<CitaRs> listarCitasPorMedico(@RequestParam Long medicoId);
    
    @RequestMapping(
        value = "/por-estado",
        produces = {"application/json"},
        method = RequestMethod.GET)
    List<CitaRs> listarCitasPorEstado(@RequestParam String estado);
}