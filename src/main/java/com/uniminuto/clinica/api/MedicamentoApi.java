package com.uniminuto.clinica.api;

import com.uniminuto.clinica.entity.Medicamento;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(path = "/medicamento", produces = MediaType.APPLICATION_JSON_VALUE)
public interface MedicamentoApi {

    @GetMapping(path = "/listar")
    ResponseEntity<List<Medicamento>> listarTodos();

    @GetMapping(path = "/{id}")
    ResponseEntity<Medicamento> buscarPorId(@PathVariable("id") Long id);

    @PostMapping(path = "/guardar", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Medicamento> guardar(@RequestBody Medicamento medicamento);

    @PutMapping(path = "/actualizar/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Medicamento> actualizar(@PathVariable("id") Long id, @RequestBody Medicamento medicamento);

    @DeleteMapping(path = "/eliminar/{id}")
    ResponseEntity<Void> eliminar(@PathVariable("id") Long id);
}
