package com.uniminuto.clinica.api;

import com.uniminuto.clinica.entity.Auditoria;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * API para consultar registros de auditoría.
 * 
 * @author Sistema
 */
@CrossOrigin(origins = "*")
@RequestMapping("/auditoria")
public interface AuditoriaApi {

    @RequestMapping(value = "/listar",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<Auditoria>> listarAuditorias(
            @RequestParam(required = false) String tipoEvento
    );
}

