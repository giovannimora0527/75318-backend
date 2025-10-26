package com.uniminuto.clinica.api;

import com.uniminuto.clinica.entity.FormulaMedica;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author tu_nombre
 */
@CrossOrigin(origins = "*")
@RequestMapping("/formula-medica")
public interface FormulaMedicaApi {

    @RequestMapping(value = "/listar",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<FormulaMedica>> listarFormulas();

    @RequestMapping(value = "/guardar",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<FormulaMedica> guardarFormula(@RequestBody FormulaMedica formula)
            throws BadRequestException;

    @RequestMapping(value = "/actualizar/{id}",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.PUT)
    ResponseEntity<FormulaMedica> actualizarFormula(@PathVariable Long id, @RequestBody FormulaMedica formula)
            throws BadRequestException;

    @RequestMapping(value = "/eliminar/{id}",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.DELETE)
    ResponseEntity<Void> eliminarFormula(@PathVariable Long id)
            throws BadRequestException;
}
