package com.uniminuto.clinica.service;

import com.uniminuto.clinica.model.AutenticatorRs;
import com.uniminuto.clinica.model.AuthenticatorRq;
import com.uniminuto.clinica.model.RecuperarPasswordRq;
import com.uniminuto.clinica.model.RespuestaRs;
import org.apache.coyote.BadRequestException;

import javax.mail.MessagingException;

public interface AutenticarService {

    // ✅ CORREGIDO: Firma limpia y válida
    AutenticatorRs autenticar(
            AuthenticatorRq request,
            String ipAddress,
            String userAgent
    ) throws BadRequestException;

    RespuestaRs recuperarPassword(
            RecuperarPasswordRq request,
            String ipAddress,
            String userAgent
    ) throws MessagingException;

}
