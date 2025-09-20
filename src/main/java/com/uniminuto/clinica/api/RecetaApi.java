package com.uniminuto.clinica.api;

import com.uniminuto.clinica.entity.Receta;
import com.uniminuto.clinica.model.RecetaRq;
import com.uniminuto.clinica.model.RespuestaRs;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RequestMapping("/receta")
public interface RecetaApi {
    @RequestMapping(value = "/listar",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<Receta>> listarTodasRecetas();

    @RequestMapping(value = "/listar-desc",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<Receta>> listarRecetasDesc();

    @RequestMapping(value = "/listar-por-cita",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<Receta>> listarRecetasPorCita(
            @RequestParam Long citaId
    ) throws BadRequestException;

    @RequestMapping(value = "/listar-por-medicamento",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<Receta>> listarRecetasPorMedicamento(
            @RequestParam Integer medicamentoId
    );

    @RequestMapping(value = "/buscar",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<Receta> buscarRecetaPorId(
            @RequestParam Long id
    ) throws BadRequestException;

    @RequestMapping(value = "/guardar",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<RespuestaRs> guardarReceta(
            @RequestBody RecetaRq recetaRq
    ) throws BadRequestException;
}
