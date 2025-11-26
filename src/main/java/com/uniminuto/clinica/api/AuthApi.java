package com.uniminuto.clinica.api;

import com.uniminuto.clinica.model.LoginRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface AuthApi {

    @PostMapping(
            value = "/login",
            produces = "application/json",
            consumes = "application/json"
    )
    ResponseEntity<?> login(@RequestBody LoginRequest request);
}

