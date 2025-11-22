package com.uniminuto.clinica.api;

public interface PasswordResetApi {

    void enviarToken(String username);

    boolean validarToken(String token);

    void actualizarPassword(String token, String nuevaPassword);
}


