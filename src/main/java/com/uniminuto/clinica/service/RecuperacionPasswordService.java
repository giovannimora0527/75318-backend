package com.uniminuto.clinica.service;

import com.uniminuto.clinica.model.RecuperacionPasswordResponse;
import org.apache.coyote.BadRequestException;

public interface RecuperacionPasswordService {
    RecuperacionPasswordResponse solicitarRecuperacion(String username, String ip) throws BadRequestException;
    String generarPasswordTemporal();
    void enviarEmailRecuperacion(String email, String passwordTemporal, String username);
}