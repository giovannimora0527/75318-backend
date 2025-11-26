package com.uniminuto.clinica.service;

import com.uniminuto.clinica.entity.Usuario;

public interface AuthService {
    Usuario login(String username, String password);
}
