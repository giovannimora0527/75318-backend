package com.uniminuto.clinica.api;

import com.uniminuto.clinica.entity.Medicamento;
import com.uniminuto.clinica.entity.Paciente;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@CrossOrigin(origins = "*")
@RequestMapping("/paciente")
public interface PacienteApi {

    @RequestMapping(
            value = "/listar",
            produces = {"application/json"},
            method = RequestMethod.GET
    )
    ResponseEntity<List<Paciente>> listarPacientes();

    @RequestMapping(
            value = "/buscar",
            produces = {"application/json"},
            method = RequestMethod.GET
    )
    ResponseEntity<Paciente> buscarPorNumeroDocumento(
            @RequestParam String numeroDocumento
    );

    @RequestMapping(
            value = "/fechaNacimiento",
            produces = {"application/json"},
            method = RequestMethod.GET
    )
    ResponseEntity<List<Paciente>> listarPacientesPorFechaNacimientoDesc();
    
    @PostMapping("/guardar")
    ResponseEntity<Paciente> guardarPaciente(@RequestBody Paciente paciente);

    @PutMapping("/actualizar/{id}")
    ResponseEntity<Paciente> actualizarPaciente(@PathVariable Long id, @RequestBody Paciente paciente);


}
