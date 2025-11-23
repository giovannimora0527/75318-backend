package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.api.UsuarioApi; // Si usas interfaz generada, mantenlo
import com.uniminuto.clinica.entity.Usuario;
import com.uniminuto.clinica.model.RespuestaRs;
import com.uniminuto.clinica.model.UsuarioRq;
import com.uniminuto.clinica.service.UsuarioService;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*; // Importante para las anotaciones REST

/**
 * Controlador corregido con rutas explícitas
 */
@RestController
@RequestMapping("/usuario") // 1. Esto define la base: .../clinica/v1/usuario
@CrossOrigin(origins = "*") // Opcional: Para evitar problemas de CORS si pruebas local
public class UsuarioApiController implements UsuarioApi {

    @Autowired
    private UsuarioService usuarioService;

    @Override
    @GetMapping("/listar") // Agregado mapeo explícito
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        return ResponseEntity.ok(this.usuarioService.listarTodosLosUsuarios());
    }

    @Override
    @GetMapping("/rol/{rol}")
    public ResponseEntity<List<Usuario>> listarUsuariosPorRol(@PathVariable("rol") String rol) {
        return ResponseEntity.ok(this.usuarioService.encontrarPorRol(rol));
    }

    @Override
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<Usuario> buscarUsuarioPorNombre(@PathVariable("nombre") String nombre)
            throws BadRequestException {
        return ResponseEntity.ok(this.usuarioService.encontrarPorNombre(nombre));
    }

    @Override
    @GetMapping("/estado/{activo}")
    public ResponseEntity<List<Usuario>> buscarUsuariosPorEstado(@PathVariable("activo") Integer activo)
            throws BadRequestException {
        return ResponseEntity.ok(this.usuarioService.buscarPorEstado(activo));
    }

    /**
     * CORRECCIÓN CLAVE: 
     * Angular llama a '/actualizar', así que mapeamos esa ruta aquí.
     * También mapeamos '/guardar' por si acaso lo usas en otra pantalla.
     */
    @Override
    @PostMapping(value = {"/guardar", "/actualizar"}) 
    public ResponseEntity<RespuestaRs> guardarUsuario(@RequestBody UsuarioRq usuarioNuevo)
            throws BadRequestException {
        return ResponseEntity.ok(this.usuarioService.guardarUsuario(usuarioNuevo));
    }
}