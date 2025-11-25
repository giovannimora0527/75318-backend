package com.uniminuto.clinica.api;

import com.uniminuto.clinica.entity.Medico;
import com.uniminuto.clinica.model.RespuestaRs;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@CrossOrigin(origins = "*")
@RequestMapping("/medico")
public interface MedicoApi {

    @GetMapping("/listar")
    ResponseEntity<List<Medico>> listarMedicos();

    @PostMapping("/guardar")
    ResponseEntity<RespuestaRs> guardarMedico(@RequestBody Medico medico);

    @PutMapping("/actualizar")
    ResponseEntity<RespuestaRs> actualizarMedico(@RequestBody Medico medico);

    @GetMapping("/especialidad/{codigo}")
    ResponseEntity<List<Medico>> listarMedicosporEspecialidad(@PathVariable String codigo);
}