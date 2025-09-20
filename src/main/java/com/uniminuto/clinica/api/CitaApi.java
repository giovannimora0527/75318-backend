package com.uniminuto.clinica.api;

import com.uniminuto.clinica.model.RespuestaRs;
import com.uniminuto.clinica.model.CitaRq;
import com.uniminuto.clinica.entity.Cita;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RequestMapping("/cita")
public interface CitaApi {
    @RequestMapping(value = "/listar-por-paciente",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<Cita>> listarCitasporPaciente(
            @RequestParam Long id
    ) throws BadRequestException;

    @RequestMapping(value = "/listar-por-medico",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<Cita>> listarCitasporMedico(
            @RequestParam Long id
    ) throws BadRequestException;

    @RequestMapping(value = "/guardar",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<RespuestaRs> guardarCita(
            @RequestBody CitaRq citaNuevo
    ) throws BadRequestException;

    @RequestMapping(value = "/listar-desc",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<Cita>> listarCitasDesc();
}
            @RequestBody CitaRq citaRq)
            throws BadRequestException;
    @RequestMapping(value = "/listar-recientes",
            produces = {"application/json"},
            method = RequestMethod.GET
    )
    ResponseEntity<List<Cita>> listarCitasRecientes();
}
