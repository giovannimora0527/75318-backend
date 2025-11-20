package com.uniminuto.clinica.api;

import com.uniminuto.clinica.entity.Paciente;
import com.uniminuto.clinica.model.PacienteRq;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

/**
 *
 * @author jartunduaga
 */
@CrossOrigin(origins = "*")
@RequestMapping("/paciente")
@SecurityRequirement(name = "bearer-jwt")
public interface PacienteApi {

    @RequestMapping(value = "/listar",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<Paciente>> listarPaciente();

    @RequestMapping(value = "/por_documento",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<Paciente> encontrarPorDocumento(
            @RequestParam String numeroDocumento
    ) throws BadRequestException;

    @RequestMapping(value = "/por-fecha-nacimiento",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<Paciente>> listarPacientePorFechaNacimiento(
            @RequestParam(defaultValue = "asc") String orden);

    @RequestMapping(value = "/guardar",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<Paciente> guardarPaciente(@RequestBody PacienteRq pacienteRq, javax.servlet.http.HttpServletRequest request) throws BadRequestException;

    @RequestMapping(value = "/actualizar/{id}",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.PUT)
    ResponseEntity<Paciente> actualizarPaciente(
            @PathVariable Long id,
            @RequestBody PacienteRq pacienteRq,
            javax.servlet.http.HttpServletRequest request) throws BadRequestException;

}
