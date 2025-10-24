package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.api.RecetaApi;
import com.uniminuto.clinica.entity.Receta;
import com.uniminuto.clinica.model.RecetaRq;
import com.uniminuto.clinica.service.RecetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class RecetaApiController implements RecetaApi {

    @Autowired
    private RecetaService recetaService;

    @Override
    public ResponseEntity<Receta> guardarReceta(RecetaRq recetaRq) {
        Receta nuevaReceta = recetaService.guardarReceta(recetaRq);
        return ResponseEntity.ok(nuevaReceta);
    }

    @Override
    public ResponseEntity<List<Receta>> listarPorReceta(Long citaId) {
        return ResponseEntity.ok(recetaService.listarPorCita(citaId));
    }

    @Override
    public ResponseEntity<List<Receta>> listaRecetas() {
        return ResponseEntity.ok(recetaService.listaRecetas());
    }

    @Override
    public ResponseEntity<Receta> actualizarReceta(Long id, RecetaRq recetaRq) {
        Receta recetaActualizada = recetaService.actualizarReceta(id, recetaRq);
        return ResponseEntity.ok(recetaActualizada);
    }

    @Override
    public ResponseEntity<Void> eliminarReceta(Long id) {
        recetaService.eliminarReceta(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Receta> buscarPorId(Long id) {
        Receta receta = recetaService.buscarPorId(id);
        return ResponseEntity.ok(receta);
    }
}
