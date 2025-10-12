package com.uniminuto.clinica.controller;

import com.uniminuto.clinica.api.MedicamentoApi;
import com.uniminuto.clinica.entity.Medicamento;
import com.uniminuto.clinica.service.MedicamentoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
public class MedicamentoApiController implements MedicamentoApi {

    private final MedicamentoService medicamentoService;

    public MedicamentoApiController(MedicamentoService medicamentoService) {
        this.medicamentoService = medicamentoService;
    }

    @Override
    public ResponseEntity<List<Medicamento>> listarTodos() {
        List<Medicamento> lista = medicamentoService.listarTodos();
        return ResponseEntity.ok(lista);
    }

    @Override
    public ResponseEntity<Medicamento> buscarPorId(Long id) {
        return medicamentoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<Medicamento> guardar(Medicamento medicamento) {
        Medicamento creado = medicamentoService.guardar(medicamento);
        // opcional: devolver Location con la URI del recurso creado
        try {
            URI location = new URI("/clinica/v1/medicamento/" + creado.getId());
            return ResponseEntity.created(location).body(creado);
        } catch (Exception e) {
            return ResponseEntity.ok(creado);
        }
    }

    @Override
    public ResponseEntity<Medicamento> actualizar(Long id, Medicamento medicamento) {
        Medicamento actualizado = medicamentoService.actualizar(id, medicamento);
        return ResponseEntity.ok(actualizado);
    }

    @Override
    public ResponseEntity<Void> eliminar(Long id) {
        medicamentoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
