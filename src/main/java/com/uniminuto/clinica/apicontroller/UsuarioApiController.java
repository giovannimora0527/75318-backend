package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.api.UsuarioApi;
import com.uniminuto.clinica.entity.Usuario;
import com.uniminuto.clinica.service.UsuarioService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import java.util.NoSuchElementException;

/**
 *
 * @author lmora
 */
@RestController
public class UsuarioApiController implements UsuarioApi {
    
    @Autowired
    private UsuarioService usuarioService;

    @Override
    public ResponseEntity<List<Usuario>> obtenerUsuarios() {
        return ResponseEntity.ok(this.usuarioService.obtenerUsuarios());
    }

    @Override
    public ResponseEntity<Usuario> buscarPorDocumento(String documento) {
        return usuarioService.buscarPorDocumento(documento)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new NoSuchElementException("Usuario no encontrado con documento: " + documento));
    }
}
