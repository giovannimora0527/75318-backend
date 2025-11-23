package com.uniminuto.clinica.api;

import com.uniminuto.clinica.model.RecuperacionPasswordRequest;
import com.uniminuto.clinica.model.RecuperacionPasswordResponse;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/auth")
public interface RecuperacionPasswordApi {
    
    @PostMapping("/recuperar-password")
    ResponseEntity<RecuperacionPasswordResponse> recuperarPassword(
            @RequestBody RecuperacionPasswordRequest request) throws BadRequestException;
}