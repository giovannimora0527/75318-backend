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
}
