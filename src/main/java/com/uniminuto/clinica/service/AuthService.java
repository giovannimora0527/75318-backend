package com.uniminuto.clinica.service;

import com.uniminuto.clinica.entity.Usuario;
import javax.mail.MessagingException;

public interface AuthService {

    void recordLoginFailure(String usernameInput, String ipAddress);

    void recordLoginSuccess(String usernameInput, String ipAddress);

    boolean isLocked(Usuario usuario);

    String recoverPassword(String username, String ipAddress) throws MessagingException;
}
