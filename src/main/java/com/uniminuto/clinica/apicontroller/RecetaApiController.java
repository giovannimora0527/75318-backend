package com.uniminuto.clinica.apicontroller;
import com.uniminuto.clinica.api.RecetaApi;
import com.uniminuto.clinica.entity.Receta;
import com.uniminuto.clinica.service.RecetaService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/api/receta")
public class RecetaApiController implements RecetaApi {

    private final RecetaService recetaService;

    public RecetaApiController(RecetaService recetaService) {
        this.recetaService = recetaService;
    }

    @Override
    @PostMapping("/crear")
    public Receta crearReceta (@RequestBody Receta receta) {
        return recetaService.guardarReceta(receta);
    }

    @Override
    @GetMapping("/listar")
    public List<Receta> listarReceta() {
        return recetaService.obtenerTodas();
    }

    @Override
    @GetMapping("/{id}")
    public Receta obtenerRecetaPorId(@PathVariable Long id) {
        return recetaService.obtenerPorId(id);
    }

    @Override
    @GetMapping("/listar-desc")
    public ResponseEntity<List<Receta>> listarRecetaPorFechaCreacionDesc() {
    return ResponseEntity.ok(recetaService.listarRecetaPorFechaCreacionDesc());
}
}
