package com.uniminuto.clinica.api;

import com.uniminuto.clinica.entity.Cita;
import com.uniminuto.clinica.model.CitaRs;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 * @author JAVIER-CUERVO
 */
@CrossOrigin(origins = "*")
@RequestMapping("/cita")
public interface CitaApi {

    @RequestMapping(value = "/medico/{id}",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<CitaRs<List<Cita>>> listarPorMedico(@PathVariable("id") Long id);

    @RequestMapping(value = "/paciente/{id}",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<CitaRs<List<Cita>>> listarPorPaciente(@PathVariable("id") Long id);

    @RequestMapping(value = "/listar",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<CitaRs<List<Cita>>> listarCitas();

    @RequestMapping(value = "/guardar",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<CitaRs<Cita>> guardarCita(@RequestBody Cita cita);

    @RequestMapping(value = "/listar/fecha",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<CitaRs<List<Cita>>> listarPorFechaHora();

}
