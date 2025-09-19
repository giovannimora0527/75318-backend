package com.uniminuto.clinica.api;

import com.uniminuto.clinica.entity.Paciente;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 *
 * @author jartunduaga
 */
@CrossOrigin(origins = "*")
@RequestMapping("/paciente")

public interface PacienteApi {

    @RequestMapping(value = "/listar",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<Paciente>> listarPaciente();

    @RequestMapping(value = "/por_documento",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<Paciente> encontrarPorDocumento(
            @RequestParam String numeroDocumento
    ) throws BadRequestException;

    @RequestMapping(value = "/por-fecha-nacimiento",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<Paciente>> listarPacientePorFechaNacimiento(
            @RequestParam(defaultValue = "asc") String orden);

}
