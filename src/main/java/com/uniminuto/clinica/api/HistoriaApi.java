package com.uniminuto.clinica.api;

import com.uniminuto.clinica.entity.Historia;
import com.uniminuto.clinica.model.HistoriaRq;
import com.uniminuto.clinica.model.RespuestaRs;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RequestMapping("/historia")
public interface HistoriaApi {

    @RequestMapping(value = "/listar",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<Historia>> listarHistorias();


    @RequestMapping(value = "/guardar",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<RespuestaRs> guardarHistoria(
            @RequestBody HistoriaRq historiaNuevo
    ) throws BadRequestException;


    @RequestMapping(value = "/actualizar",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<RespuestaRs> actualizarHistoria(
            @RequestBody HistoriaRq historia
    ) throws BadRequestException;

    @DeleteMapping("/eliminar/{id}")
    ResponseEntity<RespuestaRs> eliminarHistoria(@PathVariable Integer idHistoria)
            throws BadRequestException;;
}