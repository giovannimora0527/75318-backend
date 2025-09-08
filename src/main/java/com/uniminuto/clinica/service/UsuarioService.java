package com.uniminuto.clinica.service;

import com.uniminuto.clinica.entity.Usuario;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author lmora
 */
public interface UsuarioService {
    List<Usuario> obtenerUsuarios(); 
    Optional<Usuario> buscarPorDocumento(String documento);
}
