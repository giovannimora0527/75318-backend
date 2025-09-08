package com.uniminuto.clinica.api;

import com.uniminuto.clinica.entity.Paciente;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

}
