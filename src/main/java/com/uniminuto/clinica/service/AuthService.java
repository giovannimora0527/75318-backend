package com.uniminuto.clinica.service;

import com.uniminuto.clinica.model.LoginRequest;
import com.uniminuto.clinica.model.LoginResponse;
import org.apache.coyote.BadRequestException;

public interface AuthService {
    LoginResponse iniciarSesion(LoginRequest request) throws BadRequestException;
    Boolean usuarioBloqueado(String username);
    void registrarIntentoLogin(String username, Boolean exitoso, String ip, String mensaje);
    Integer verificarYBloquearUsuario(String username, String ip);
}