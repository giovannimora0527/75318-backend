package com.uniminuto.clinica.service;

import com.uniminuto.clinica.entity.Usuario;
import com.uniminuto.clinica.model.UsuarioRq;
import com.uniminuto.clinica.model.RespuestaRs;

import java.util.List;

public interface UsuarioService {

    List<Usuario> listarTodosLosUsuarios();

    List<Usuario> encontrarPorRol(String rol);

    Usuario encontrarPorNombre(String nombreUsuario);

    List<Usuario> buscarPorEstado(Integer estado);

    RespuestaRs guardarUsuario(UsuarioRq usuarioNuevo);

    RespuestaRs actualizarUsuario(UsuarioRq usuarioRq);
    
    RespuestaRs recuperarPassword(String username);
    
    Usuario login(String email, String password, String ip);
}
