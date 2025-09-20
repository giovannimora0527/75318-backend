package com.uniminuto.clinica.api;

import com.uniminuto.clinica.entity.Cita;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 * @author jartunduaga
 */
@CrossOrigin(origins = "*")
@RequestMapping("/citas")
public interface CitaApi {
    @RequestMapping(
            value = "/guardar-cita",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST
    )
    ResponseEntity<Cita> guardarCita(
            @RequestParam Long pacienteId,
            @RequestParam Long medicoId,
            @RequestBody Cita cita
    );

    @RequestMapping(value = "/por-fecha",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<Cita>> listarCitaPorFecha(
            @RequestParam(defaultValue = "asc") String orden);
}
