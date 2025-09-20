/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package com.uniminuto.clinica.api;

import com.uniminuto.clinica.entity.Medico;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author crash
 */
@CrossOrigin(origins = "*")
@RequestMapping("/medico")
public interface MedicoApi {
    
    @RequestMapping(value = "/listar",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<Medico>> listarMedicos();
    
    
    @RequestMapping(value = "/listar-x-cod-esp",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<Medico>> listarMedicosporEspecialidad(
      @RequestParam String codigo
    ) throws BadRequestException;

    @RequestMapping(value = "/buscar-x-id",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<Medico> buscarMedicoId(
      @RequestParam Long id
    ) throws BadRequestException;
}
