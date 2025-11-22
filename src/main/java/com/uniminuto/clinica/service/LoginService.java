package com.uniminuto.clinica.service;

import com.uniminuto.clinica.model.LoginRequest;
import com.uniminuto.clinica.model.LoginResponse;

public interface LoginService {
    LoginResponse login(LoginRequest request, String ipOrigen);
}


