package com.uniminuto.clinica.api;

import com.uniminuto.clinica.model.RecuperarPasswordRequest;
import com.uniminuto.clinica.model.RespuestaRs;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*")
@RequestMapping("/password")
public interface PasswordResetApi {

    @RequestMapping(value = "/recuperar",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<RespuestaRs> recuperarPassword(@Valid @RequestBody RecuperarPasswordRequest request);
}