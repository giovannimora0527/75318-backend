package com.uniminuto.clinica.service;

import com.uniminuto.clinica.model.AutenticatorRs;
import com.uniminuto.clinica.model.AuthenticatorRq;
import com.uniminuto.clinica.model.ChangePasswordRq;
import com.uniminuto.clinica.model.RespuestaRs;
import org.apache.coyote.BadRequestException;

public interface AutenticarService {

    AutenticatorRs autenticar(AuthenticatorRq request) throws BadRequestException;

    RespuestaRs cambiarPassword(String username, ChangePasswordRq request) throws BadRequestException;

    Long obtenerIdDesdeToken(String token);


}
