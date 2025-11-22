package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.entity.Usuario;
import com.uniminuto.clinica.model.UsuarioRq;
import com.uniminuto.clinica.service.UsuarioService;
import com.uniminuto.clinica.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200") // Permite Angular
public class AuthApiController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody UsuarioRq loginRequest) {
        Usuario usuario = usuarioService.login(loginRequest.getUsername(), loginRequest.getPassword());
        if (usuario == null) {
            return ResponseEntity.status(401).body(Map.of("error", "Usuario o contraseña incorrecta"));
        }

        String token = JwtUtil.generateToken(usuario); // Genera JWT
        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("rol", usuario.getRol());
        response.put("username", usuario.getUsername());

        return ResponseEntity.ok(response);
    }
}
