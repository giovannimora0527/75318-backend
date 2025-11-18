package com.uniminuto.clinica.api;

import com.uniminuto.clinica.entity.Receta;
import com.uniminuto.clinica.model.RecetaRq;
import com.uniminuto.clinica.repository.RecetaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@CrossOrigin(origins = "*")
@RequestMapping("/receta")
@SecurityRequirement(name = "bearer-jwt")
public interface RecetaApi {
    @PostMapping(
            value = "/guardar-receta",
            produces = "application/json",
            consumes = "application/json"
    )
    ResponseEntity<Receta> guardarReceta(@RequestBody RecetaRq recetaRq);

    @RequestMapping(
            value = "/por-receta",
            method = RequestMethod.GET,
            produces = {"application/json"}
    )
    ResponseEntity<List<Receta>> listarPorReceta(
            @RequestParam Long citaId
    );

    @RequestMapping(
            value = "/recetas",
            method = RequestMethod.GET,
            produces = {"application/json"}
    )
    ResponseEntity<List<Receta>> listaRecetas();

    @PutMapping(
            value = "/actualizar-receta/{id}",
            produces = "application/json",
            consumes = "application/json"
    )
    ResponseEntity<Receta> actualizarReceta(@PathVariable Long id, @RequestBody RecetaRq recetaRq);

    @DeleteMapping(
            value = "/eliminar-receta/{id}",
            produces = "application/json"
    )
    ResponseEntity<Void> eliminarReceta(@PathVariable Long id);

    @RequestMapping(
            value = "/receta/{id}",
            method = RequestMethod.GET,
            produces = {"application/json"}
    )
    ResponseEntity<Receta> buscarPorId(@PathVariable Long id);

}
