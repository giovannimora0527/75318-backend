package com.uniminuto.clinica.controller;

import com.uniminuto.clinica.entity.Usuario;
import com.uniminuto.clinica.model.UsuarioRq;
import com.uniminuto.clinica.model.RespuestaRs;
import com.uniminuto.clinica.service.UsuarioService;
import com.uniminuto.clinica.model.LoginRq;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin("*")
public class UsuarioApiController {

    @Autowired
    private UsuarioService usuarioService;

    // ================================
    // LISTAR TODOS
    // ================================
    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        return ResponseEntity.ok(usuarioService.listarTodosLosUsuarios());
    }

    // ================================
    // BUSCAR POR USERNAME
    // ================================
    @GetMapping("/username/{username}")
    public ResponseEntity<Usuario> buscarPorNombre(@PathVariable String username) {
        return ResponseEntity.ok(usuarioService.encontrarPorNombre(username));
    }

    // ================================
    // BUSCAR POR ROL
    // ================================
    @GetMapping("/rol/{rol}")
    public ResponseEntity<List<Usuario>> buscarPorRol(@PathVariable String rol) {
        return ResponseEntity.ok(usuarioService.encontrarPorRol(rol));
    }

    // ================================
    // BUSCAR POR ESTADO
    // ================================
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Usuario>> buscarPorEstado(@PathVariable Integer estado) {
        return ResponseEntity.ok(usuarioService.buscarPorEstado(estado));
    }

    // ================================
    // CREAR USUARIO
    // ================================
    @PostMapping
    public ResponseEntity<RespuestaRs> crearUsuario(@RequestBody UsuarioRq usuarioRq) {
        return ResponseEntity.ok(usuarioService.guardarUsuario(usuarioRq));
    }

    // ================================
    // ACTUALIZAR USUARIO
    // ================================
    @PutMapping("/{id}")
    public ResponseEntity<RespuestaRs> actualizar(
            @PathVariable Long id,
            @RequestBody UsuarioRq usuarioRq
    ) {
        usuarioRq.setId(id);
        return ResponseEntity.ok(usuarioService.actualizarUsuario(usuarioRq));
    }

    // ================================
    // RECUPERACIÓN DE CONTRASEÑA
    // ================================
    @PostMapping("/recuperar-password")
    public ResponseEntity<RespuestaRs> recuperarPassword(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        return ResponseEntity.ok(usuarioService.recuperarPassword(username));
    }
    
    // ================================
// LOGIN CON CONTROL DE INTENTOS
// ================================
@PostMapping("/login")
public ResponseEntity<RespuestaRs> login(@RequestBody LoginRq request) {
    try {
        usuarioService.login(request.getUsername(), request.getPassword(), request.getIp());
        return ResponseEntity.ok(new RespuestaRs(200, "Login exitoso"));
    } catch (RuntimeException e) {
        return ResponseEntity.status(401).body(new RespuestaRs(401, e.getMessage()));
    }
}

}