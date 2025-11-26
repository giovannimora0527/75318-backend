package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.api.AuthApi;
import com.uniminuto.clinica.entity.Usuario;
import com.uniminuto.clinica.model.LoginRequest;
import com.uniminuto.clinica.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthApiController {

    private final AuthService authService;

    public AuthApiController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            Usuario usuario = authService.login(request.getUsername(), request.getPassword());

            // Crear respuesta con token + datos
            Map<String, Object> response = new HashMap<>();
            response.put("token", usuario.getToken()); // token generado en AuthService
            response.put("rol", usuario.getRol());
            response.put("username", usuario.getUsername());
            response.put("email", usuario.getEmail()); // si tienes nombre en usuario

            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body(Map.of("error", e.getMessage()));
        }
    }
}


