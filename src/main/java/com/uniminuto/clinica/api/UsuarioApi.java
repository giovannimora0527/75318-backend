package com.uniminuto.clinica.api;

import com.uniminuto.clinica.entity.Usuario;
import com.uniminuto.clinica.model.RespuestaRs;
import com.uniminuto.clinica.model.UsuarioRq;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

@CrossOrigin(origins = "*")
@RequestMapping("/usuario")
public interface UsuarioApi {

    // ========================================
    // LISTAR TODOS LOS USUARIOS
    // ========================================
    @GetMapping("/listar")
    ResponseEntity<List<Usuario>> listarUsuarios();


    // ========================================
    // LISTAR POR ROL
    // ========================================
    @GetMapping("/listar-rol")
    ResponseEntity<List<Usuario>> listarUsuariosPorRol(
            @RequestParam String rol
    );


    // ========================================
    // BUSCAR POR NOMBRE
    // ========================================
    @GetMapping("/buscar-nombre")
    ResponseEntity<Usuario> buscarUsuarioPorNombre(
            @RequestParam String nombre
    ) throws ResponseStatusException;


    // ========================================
    // BUSCAR POR ESTADO
    // ========================================
    @GetMapping("/buscar-estado")
    ResponseEntity<List<Usuario>> buscarUsuariosPorEstado(
            @RequestParam Integer activo
    ) throws ResponseStatusException;


    // ========================================
    // GUARDAR USUARIO NUEVO
    // ========================================
    @PostMapping(value = "/guardar", consumes = "application/json")
    ResponseEntity<RespuestaRs> guardarUsuario(
            @RequestBody UsuarioRq usuario
    ) throws ResponseStatusException;


    // ========================================
    // ACTUALIZAR USUARIO
    // ========================================
    @PostMapping(value = "/actualizar", consumes = "application/json")
    ResponseEntity<RespuestaRs> actualizarUsuario(
            @RequestBody UsuarioRq usuario
    ) throws ResponseStatusException;
    
    // ========================================
// LOGIN CON CONTROL DE INTENTOS
// ========================================
@PostMapping(value = "/login", consumes = "application/json")
ResponseEntity<RespuestaRs> login(
        @RequestParam String email,
        @RequestParam String password,
        @RequestParam(required = false) String ip
);


}
