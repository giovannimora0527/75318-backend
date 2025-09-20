package com.uniminuto.clinica.api;
import com.uniminuto.clinica.entity.Receta;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public interface RecetaApi {

    Receta crearReceta(Receta receta);

    List<Receta> listarReceta();

    Receta obtenerRecetaPorId(Long id);
    
    @RequestMapping(
            value = "/fechaCreacion",
            produces = {"application/json"},
            method = RequestMethod.GET
    )
    ResponseEntity<List<Receta>> listarRecetaPorFechaCreacionDesc();
}
