package com.uniminuto.clinica.api;

import com.uniminuto.clinica.model.MedicamentoRq;
import com.uniminuto.clinica.model.MedicamentoRs;
import com.uniminuto.clinica.model.RespuestaRs;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@CrossOrigin(origins = "*")
@RequestMapping("/medicamento")
public interface MedicamentoApi {

    @RequestMapping(
            value = "/listar",
            produces = {"application/json"},
            method = RequestMethod.GET)
    List<MedicamentoRs> listarMedicamentos();

    @RequestMapping(
            value = "/recientes",
            produces = {"application/json"},
            method = RequestMethod.GET)
    List<MedicamentoRs> listarMedicamentosRecientes();

    @RequestMapping(
            value = "/guardar",
            consumes = {"application/json"},
            produces = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<RespuestaRs> guardarMedicamento(@RequestBody MedicamentoRq medicamentoRq) throws BadRequestException;
    
    @RequestMapping(
        value = "/actualizar",
        consumes = {"application/json"},
        produces = {"application/json"},
        method = RequestMethod.POST)
    ResponseEntity<RespuestaRs> actualizarMedicamento(@RequestBody MedicamentoRq medicamentoRq) throws BadRequestException;
}