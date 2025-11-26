package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.api.PasswordResetApi;
import com.uniminuto.clinica.model.RecuperarPasswordRequest;
import com.uniminuto.clinica.model.RespuestaRs;
import com.uniminuto.clinica.service.RecuperarPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PasswordResetApiController implements PasswordResetApi {

    @Autowired
    private RecuperarPasswordService recuperarPasswordService;

    @Override
    public ResponseEntity<RespuestaRs> recuperarPassword(RecuperarPasswordRequest request) {
        return ResponseEntity.ok(recuperarPasswordService.recuperarPassword(request));
    }
}