package com.uniminuto.clinica.api;

import com.uniminuto.clinica.model.LoginRequest;
import com.uniminuto.clinica.model.LoginResponse;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/auth")
public interface AuthApi {
    
    @PostMapping("/login")
    ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) 
            throws BadRequestException;
}