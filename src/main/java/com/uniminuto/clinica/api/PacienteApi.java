package com.uniminuto.clinica.api;

import com.uniminuto.clinica.model.PacienteRq;
import com.uniminuto.clinica.model.PacienteRs;
import org.springframework.web.bind.annotation.*;
import org.apache.coyote.BadRequestException; 
import java.util.List;
import java.util.Optional;

@RequestMapping("/api/pacientes")
public interface PacienteApi {

    @GetMapping
    List<PacienteRs> obtenerTodos();

    @GetMapping("/{documento}")
    Optional<PacienteRs> buscarPorDocumento(@PathVariable String documento);

    @PostMapping
    PacienteRs guardar(@RequestBody PacienteRq paciente) throws BadRequestException; 

    @DeleteMapping("/{id}")
    void eliminar(@PathVariable Long id);
    
    @GetMapping("/ordenados-por-nacimiento")
    List<PacienteRs> obtenerPacientesOrdenadosPorFechaNacimiento();
}