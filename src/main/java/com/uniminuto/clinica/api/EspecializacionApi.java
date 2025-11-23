package com.uniminuto.clinica.api;

import com.uniminuto.clinica.entity.Especializacion;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody; // Necesario para el cuerpo de la petición (POST/PUT)
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@CrossOrigin(origins = "*")
@RequestMapping("/especializacion")
public interface EspecializacionApi {
    
 
    @RequestMapping(value = "/listar",
            produces = {"application/json"},
            method = RequestMethod.GET) 
    ResponseEntity<List<Especializacion>> listarEspecializaciones();
    

    @RequestMapping(value = "/buscar-por-codigo",
            produces = {"application/json"},
            method = RequestMethod.GET) 
    ResponseEntity<Especializacion> buscarPorCodigo(
      @RequestParam String codigo
    ) throws BadRequestException;

    
    @RequestMapping(value = "/guardar",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST) 
    ResponseEntity<Especializacion> GuardarEspecializacion(
        @RequestBody Especializacion especializacion 
    ) throws BadRequestException;

    
    @RequestMapping(value = "/{id}", 
            method = RequestMethod.DELETE)
    ResponseEntity<Void> eliminarEspecializacion(
        @PathVariable Long id 
    ) throws BadRequestException;
}