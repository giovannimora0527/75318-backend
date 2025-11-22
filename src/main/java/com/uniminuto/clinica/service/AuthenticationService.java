package com.uniminuto.clinica.service;

public interface AuthenticationService {

    /**
     * Realiza el proceso de login.
     * @param username nombre del usuario.
     * @param password contraseña ingresada.
     */
    void login(String username, String password);
}
