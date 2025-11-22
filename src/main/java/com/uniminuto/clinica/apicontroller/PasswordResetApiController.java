package com.uniminuto.clinica.api.controller;

import com.uniminuto.clinica.api.PasswordResetApi;
import com.uniminuto.clinica.service.PasswordResetService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/password")
public class PasswordResetApiController implements PasswordResetApi {

    private final PasswordResetService passwordResetService;

    @Override
    @PostMapping("/enviar-token")
    public void enviarToken(@RequestParam String username) {
        passwordResetService.enviarToken(username);
    }

    @Override
    @GetMapping("/validar")
    public boolean validarToken(@RequestParam String token) {
        return passwordResetService.validarToken(token);
    }

    @Override
    @PostMapping("/actualizar")
    public void actualizarPassword(
            @RequestParam String token,
            @RequestParam String nuevaPassword) {
        passwordResetService.actualizarPassword(token, nuevaPassword);
    }
}
