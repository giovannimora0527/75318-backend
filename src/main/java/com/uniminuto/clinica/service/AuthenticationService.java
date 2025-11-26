package com.uniminuto.clinica.service;

import com.uniminuto.clinica.model.AuthenticatorRq;
import com.uniminuto.clinica.model.AuthenticatorRs;
import org.apache.coyote.BadRequestException;

public interface AuthenticationService {

    AuthenticatorRs autenticar(AuthenticatorRq request) throws BadRequestException;
}