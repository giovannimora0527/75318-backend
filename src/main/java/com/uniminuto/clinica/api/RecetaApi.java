package com.uniminuto.clinica.api;

import com.uniminuto.clinica.model.RecetaRq;
import com.uniminuto.clinica.model.RespuestaRs;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "*")
@RequestMapping("/receta")
public interface RecetaApi {

    @PostMapping("/guardar")
    ResponseEntity<RespuestaRs> guardarReceta(@RequestBody RecetaRq recetaNueva) throws BadRequestException;
}