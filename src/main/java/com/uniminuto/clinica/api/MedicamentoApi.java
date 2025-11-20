package com.uniminuto.clinica.api;

import com.uniminuto.clinica.entity.Medicamento;
import com.uniminuto.clinica.model.MedicamentoRq;
import com.uniminuto.clinica.model.RespuestaRs;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@CrossOrigin(origins = "*")
@RequestMapping("/medicamento")
@SecurityRequirement(name = "bearer-jwt")
public interface MedicamentoApi {

    @RequestMapping(value = "/listar",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<Medicamento>> listarMedicamentos();

    @RequestMapping(value = "/crear",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<RespuestaRs> crearMedicamento(
            @RequestBody MedicamentoRq medicamentoRq,
            javax.servlet.http.HttpServletRequest request
    ) throws BadRequestException;

    @RequestMapping(value = "/actualizar/{id}",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.PUT)
    ResponseEntity<RespuestaRs> actualizarMedicamento(
            @PathVariable Long id,
            @RequestBody MedicamentoRq medicamentoRq,
            javax.servlet.http.HttpServletRequest request
    ) throws BadRequestException;
}
