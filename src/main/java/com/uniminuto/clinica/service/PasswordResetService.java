package com.uniminuto.clinica.service;


public interface PasswordResetService {

    void enviarToken(String username);

    boolean validarToken(String token);

    void actualizarPassword(String token, String nuevaPassword);
}

