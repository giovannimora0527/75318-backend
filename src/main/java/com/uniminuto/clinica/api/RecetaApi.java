package com.uniminuto.clinica.api;

import com.uniminuto.clinica.entity.Receta;
import org.springframework.http.ResponseEntity;
import java.util.List;

public interface RecetaApi {

    ResponseEntity<?> crearReceta(Receta receta);

    List<Receta> listarReceta();

    ResponseEntity<Receta> obtenerRecetaPorId(Long id);

    ResponseEntity<List<Receta>> listarRecetaPorFechaCreacionDesc();
}
