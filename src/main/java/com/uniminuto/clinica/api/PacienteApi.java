package com.uniminuto.clinica.api;

import com.uniminuto.clinica.entity.Paciente;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


  @author crash
@CrossOrigin(origins = "*")
@RequestMapping("/paciente")
public interface PacienteApi {

    @GetMapping(value = "/obtener-todos", produces = "application/json")
    ResponseEntity<List<Paciente>> obtenerPacientes();

    @GetMapping(value = "/consultar-por-doc", produces = "application/json")
    ResponseEntity<Paciente> buscarPorDocumento(
            @RequestParam(name = "numeroDocumento") String numeroDocumento
    ) throws BadRequestException;
}
