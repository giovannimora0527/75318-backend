package com.uniminuto.clinica.controller;

import com.uniminuto.clinica.entity.Usuario;
import com.uniminuto.clinica.model.UsuarioRq;
import com.uniminuto.clinica.model.LoginRq;
import com.uniminuto.clinica.model.LoginRs;
import com.uniminuto.clinica.service.UsuarioService;
import com.uniminuto.clinica.service.JwtService;
import com.uniminuto.clinica.model.RespuestaRs;


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

    @Autowired
    private JwtService jwtService;

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
    public ResponseEntity<?> crearUsuario(@RequestBody UsuarioRq usuarioRq) {
        return ResponseEntity.ok(usuarioService.guardarUsuario(usuarioRq));
    }

    // ================================
    // ACTUALIZAR USUARIO
    // ================================
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(
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
    public ResponseEntity<?> recuperarPassword(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        return ResponseEntity.ok(usuarioService.recuperarPassword(username));
    }

    // ================================
    // LOGIN CON JWT
@PostMapping("/login")
public ResponseEntity<?> login(@RequestBody LoginRq request) {
    try {
        Usuario usuario = usuarioService.login(request.getUsername(), request.getPassword(), request.getIp());
        return ResponseEntity.ok(usuario); // solo devuelve el usuario
    } catch (RuntimeException e) {
        return ResponseEntity.status(401).body(e.getMessage());
    }
}




}
