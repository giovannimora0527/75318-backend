package com.uniminuto.clinica.api;

import com.uniminuto.clinica.entity.Medico;

import java.util.List;

import com.uniminuto.clinica.model.MedicamentoRq;
import com.uniminuto.clinica.model.MedicoRq;
import com.uniminuto.clinica.model.RespuestaRs;
import com.uniminuto.clinica.model.UsuarioRq;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 *
 * @author lmora
 */
@CrossOrigin(origins = "*")
@RequestMapping("/medico")
public interface MedicoApi {

    @RequestMapping(value = "/listar",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<Medico>> listarMedicos() throws BadRequestException;


    @RequestMapping(value = "/listar-x-cod-esp",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<Medico>> listarMedicosporEspecialidad(
            @RequestParam String codigo
    ) throws BadRequestException;

    @RequestMapping(value = "/guardar",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<RespuestaRs> guardarMedico(
            @RequestBody @Valid MedicoRq medicoRq
    ) throws BadRequestException;

    @RequestMapping(value = "/actualizar",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<RespuestaRs> actualizarMedico(
            @RequestBody MedicoRq medicoRq
    ) throws BadRequestException;

    @DeleteMapping("/eliminar/{id}")
    ResponseEntity<RespuestaRs> eliminarMedico(@PathVariable Integer idMedico)
            throws BadRequestException;;
}
