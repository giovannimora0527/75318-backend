package com.uniminuto.clinica.api;


import com.uniminuto.clinica.model.LoginRequest;
import com.uniminuto.clinica.model.LoginResponse;

public interface LoginApi {
    LoginResponse login(LoginRequest request, String ipOrigen);
}

