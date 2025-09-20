package com.uniminuto.clinica.api;

import com.uniminuto.clinica.entity.Receta;
import com.uniminuto.clinica.repository.RecetaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "*")
@RequestMapping("/receta")
public interface RecetaApi {
    @RequestMapping(
            value = "/guardar-receta",
            method = RequestMethod.POST,
            produces = {"application/json"},
            consumes = {"application/json"}
    )
    ResponseEntity<Receta> gardarReceta(
            @RequestParam Long citaId,
            @RequestBody Receta receta
    );

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

}
