package com.uniminuto.clinica.service;

import com.uniminuto.clinica.model.AutenticatorRs;
import com.uniminuto.clinica.model.AuthenticatorRq;
import com.uniminuto.clinica.model.RespuestaRs;
import org.apache.coyote.BadRequestException;

import javax.servlet.http.HttpServletRequest;

public interface AutenticarService {

    AutenticatorRs autenticar(AuthenticatorRq request, HttpServletRequest httpRequest) throws BadRequestException;
    
    RespuestaRs recuperarContrasena(String username, HttpServletRequest request) throws BadRequestException;
}
