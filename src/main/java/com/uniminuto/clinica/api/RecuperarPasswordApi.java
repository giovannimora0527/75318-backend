package com.uniminuto.clinica.api;

import com.uniminuto.clinica.model.RecuperarPasswordRq;
import com.uniminuto.clinica.model.RespuestaRs;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.validation.Valid;

@RequestMapping("/auth")
public interface RecuperarPasswordApi {
    @PostMapping("/recuperar-contrasena")
    ResponseEntity<RespuestaRs> recuperarPassword(@RequestBody RecuperarPasswordRq request) throws BadRequestException;
}
