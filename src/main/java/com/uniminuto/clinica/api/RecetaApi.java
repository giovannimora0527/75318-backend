package com.uniminuto.clinica.api;

import com.uniminuto.clinica.entity.Receta;
import org.springframework.http.ResponseEntity;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface RecetaApi {

    ResponseEntity<?> crearReceta(Receta receta);

    List<Receta> listarReceta();

    ResponseEntity<Receta> obtenerRecetaPorId(Long id);

    ResponseEntity<List<Receta>> listarRecetaPorFechaCreacionDesc();
    
        
    @PutMapping(path = "/actualizar/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Receta> actualizar(@PathVariable("id") Long id, @RequestBody Receta receta);
}
