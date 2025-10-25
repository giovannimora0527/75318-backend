/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.uniminuto.clinica.api;

import com.uniminuto.clinica.model.RecetaRq;
import com.uniminuto.clinica.model.RecetaRs;
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
@RequestMapping("/receta")
public interface RecetaApi {

    @RequestMapping(
            value = "/listar",
            produces = {"application/json"},
            method = RequestMethod.GET)
    List<RecetaRs> listarRecetas();

    @RequestMapping(
            value = "/recientes",
            produces = {"application/json"},
            method = RequestMethod.GET)
    List<RecetaRs> listarRecetasRecientes();

    @RequestMapping(
            value = "/guardar",
            consumes = {"application/json"},
            produces = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<RespuestaRs> guardarReceta(@RequestBody RecetaRq recetaRq) throws BadRequestException;
    
    @RequestMapping(
        value = "/actualizar",
        consumes = {"application/json"},
        produces = {"application/json"},
        method = RequestMethod.POST)
    ResponseEntity<RespuestaRs> actualizarReceta(@RequestBody RecetaRq recetaRq) throws BadRequestException;
    
    @RequestMapping(
        value = "/por-cita",
        produces = {"application/json"},
        method = RequestMethod.GET)
    List<RecetaRs> listarRecetasPorCita(@RequestParam Long citaId);
    
    @RequestMapping(
        value = "/por-medicamento",
        produces = {"application/json"},
        method = RequestMethod.GET)
    List<RecetaRs> listarRecetasPorMedicamento(@RequestParam Long medicamentoId);
}