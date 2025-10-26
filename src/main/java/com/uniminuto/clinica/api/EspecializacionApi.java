package com.uniminuto.clinica.api;

import com.uniminuto.clinica.entity.Especializacion;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RequestMapping("/especializacion")
public interface EspecializacionApi {
    
    @RequestMapping(
        value = "/listar",
        produces = {"application/json"},
        method = RequestMethod.GET
    )
    ResponseEntity<List<Especializacion>> listarEspecializaciones();
    
    @RequestMapping(
        value = "/buscar-por-codigo",
        produces = {"application/json"},
        method = RequestMethod.GET
    )
    ResponseEntity<Especializacion> buscarPorCodigo(
        @RequestParam String codigo
    ) throws BadRequestException;
    
    @PutMapping(
        path = "/actualizar/{id}", 
        consumes = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<Especializacion> actualizar(
        @PathVariable("id") Long id, 
        @RequestBody Especializacion especializacion
    );

    @PostMapping(
        path = "/crear", 
        consumes = MediaType.APPLICATION_JSON_VALUE, 
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<?> crearEspecializacion(@RequestBody Especializacion especializacion);
}
