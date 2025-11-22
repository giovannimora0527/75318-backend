package com.uniminuto.clinica.api;

import com.uniminuto.clinica.model.AutenticatorRs;
import com.uniminuto.clinica.model.AuthenticatorRq;
import com.uniminuto.clinica.model.RecuperarPasswordRq;
import com.uniminuto.clinica.model.RespuestaRs;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;


@RequestMapping("/auth")
public interface AutenticarApi {
    @PostMapping("/login")
    ResponseEntity<AutenticatorRs> autenticar(@Valid @RequestBody AuthenticatorRq request) throws BadRequestException;


    @PostMapping("/recuperar-password")
    ResponseEntity<RespuestaRs> recuperarPassword(
            @Valid @RequestBody RecuperarPasswordRq request,
            HttpServletRequest httpRequest);
}
