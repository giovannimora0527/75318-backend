package com.uniminuto.clinica.api;

import com.uniminuto.clinica.entity.Paciente;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/api/pacientes")
public interface PacienteApi {

    @GetMapping
    List<Paciente> obtenerTodos();

    @GetMapping("/{documento}")
    Optional<Paciente> buscarPorDocumento(@PathVariable String documento);

    @PostMapping
    Paciente guardar(@RequestBody Paciente paciente);

    @DeleteMapping("/{id}")
    void eliminar(@PathVariable Long id);
    
    @GetMapping("/ordenados-por-nacimiento")
    List<Paciente> obtenerPacientesOrdenadosPorFechaNacimiento();
}
