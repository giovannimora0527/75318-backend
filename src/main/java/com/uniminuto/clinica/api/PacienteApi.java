package com.uniminuto.clinica.api;

import com.uniminuto.clinica.entity.Usuario;
import com.uniminuto.clinica.service.UsuarioService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuarioApi {

    private final UsuarioService usuarioService;

    public UsuarioApi(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // 1. Listar todos los usuarios
    @GetMapping
    public List<Usuario> listarUsuarios() {
        return usuarioService.listarTodos();
    }

    // 2. Buscar usuario por documento
    @GetMapping("/{documento}")
    public Optional<Usuario> buscarPorDocumento(@PathVariable String documento) {
        return usuarioService.buscarPorDocumento(documento);
    }
}
