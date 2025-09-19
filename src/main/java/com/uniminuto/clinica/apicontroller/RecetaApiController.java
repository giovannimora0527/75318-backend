package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.api.RecetaApi;
import com.uniminuto.clinica.entity.Receta;
import com.uniminuto.clinica.model.RecetaRq;
import com.uniminuto.clinica.model.RespuestaRs;
import com.uniminuto.clinica.service.RecetaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor

public class RecetaApiController implements RecetaApi {
    private final RecetaService recetaService;

    @Override
    public ResponseEntity<RespuestaRs<Receta>> guardarReceta    (RecetaRq recetaRq) {
        return ResponseEntity.ok(recetaService.guardarReceta(recetaRq));
    }

    @Override
    public ResponseEntity<List<Receta>> listarReceta() {
        return ResponseEntity.ok(recetaService.listarRecetas());
    }

    @Override
    public ResponseEntity<List<Receta>> listarRecetasOrdenadosPorFecha() {
        List<Receta> recetas = recetaService.listarRecetasOrdenadosPorFecha();
        return ResponseEntity.ok(recetas);
    }
}
