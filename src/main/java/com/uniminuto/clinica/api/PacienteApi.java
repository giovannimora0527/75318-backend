/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.uniminuto.clinica.api;

import com.uniminuto.clinica.model.PacienteRq;
import com.uniminuto.clinica.model.PacienteRs;
import com.uniminuto.clinica.model.RespuestaRs;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * 
 * @author Usuario
 */

@CrossOrigin(origins = "*")
@RequestMapping("/paciente")
public interface PacienteApi {

    @RequestMapping(
            value = "/listar",
            produces = {"application/json"},
            method = RequestMethod.GET)
    List<PacienteRs> listarPacientes();

    @RequestMapping(
            value = "/recientes",
            produces = {"application/json"},
            method = RequestMethod.GET)
    List<PacienteRs> listarPacientesRecientes();

    @RequestMapping(
            value = "/guardar",
            consumes = {"application/json"},
            produces = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<RespuestaRs> guardarPaciente(@RequestBody PacienteRq pacienteRq) throws BadRequestException;
    
    @RequestMapping(
        value = "/actualizar",
        consumes = {"application/json"},
        produces = {"application/json"},
        method = RequestMethod.POST)
    ResponseEntity<RespuestaRs> actualizarPaciente(@RequestBody PacienteRq pacienteRq) throws BadRequestException;
}
