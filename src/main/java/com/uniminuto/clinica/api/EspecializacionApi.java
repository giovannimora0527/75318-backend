package com.uniminuto.clinica.api;

import com.uniminuto.clinica.entity.Especializacion;
import java.util.List;

import com.uniminuto.clinica.model.CitaRq;
import com.uniminuto.clinica.model.EspecializacionRq;
import com.uniminuto.clinica.model.RespuestaRs;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 *
 * @author lmora
 */
@CrossOrigin(origins = "*")
@RequestMapping("/especializacion")
public interface EspecializacionApi {
    
    @RequestMapping(value = "/listar",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<Especializacion>> listarEspecializaciones() throws BadRequestException;
    
    
    @RequestMapping(value = "/buscar-por-codigo",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<Especializacion> buscarPorCodigo(
      @RequestParam String codigo
    ) throws BadRequestException;

    @RequestMapping(value = "/guardar",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<RespuestaRs> guardarEspecializacion(
            @RequestBody @Valid EspecializacionRq especializacionRq
    ) throws BadRequestException;

    @RequestMapping(value = "/actualizar",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<RespuestaRs> actualizarEspecializacion(
            @RequestBody EspecializacionRq especializacionRq
    ) throws BadRequestException;

    @DeleteMapping("/eliminar/{id}")
    ResponseEntity<RespuestaRs> eliminarEspecializacion(@PathVariable Integer idEspecializacion)
            throws BadRequestException;;
}
