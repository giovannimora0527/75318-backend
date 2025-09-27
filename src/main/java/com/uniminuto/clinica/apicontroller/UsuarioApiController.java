package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.api.UsuarioApi;
import com.uniminuto.clinica.entity.Usuario;
import com.uniminuto.clinica.model.RespuestaRs;
import com.uniminuto.clinica.model.UsuarioRq;
import com.uniminuto.clinica.service.UsuarioService;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author lmora
 */
@RestController
public class UsuarioApiController implements UsuarioApi {

    @Autowired
    private UsuarioService usuarioService;

    @Override
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        return ResponseEntity.ok(this.usuarioService.listarTodosLosUsuarios());
    }

    @Override
    public ResponseEntity<List<Usuario>> listarUsuariosPorRol(String rol) {
        return ResponseEntity.ok(this.usuarioService.encontrarPorRol(rol));
    }

    @Override
    public ResponseEntity<Usuario> buscarUsuarioPorNombre(String nombre)
            throws BadRequestException {
        return ResponseEntity.ok(this.usuarioService.encontrarPorNombre(nombre));
    }
<<<<<<< HEAD
    //Encontrar por ID usuario
    @Override
    public ResponseEntity<Usuario> encontrarPorId(Long id)
            throws BadRequestException {
        return ResponseEntity.ok(this.usuarioService.encontrarPorId(id));
    }
=======
>>>>>>> 602c738be275f7f1826ccf9ef7bcb734aeea96d1

    @Override
    public ResponseEntity<List<Usuario>> buscarUsuariosPorEstado(Integer activo)
            throws BadRequestException {
        return ResponseEntity.ok(this.usuarioService.buscarPorEstado(activo));
    }

<<<<<<< HEAD
    @Override
    public ResponseEntity<RespuestaRs> guardarUsuario(UsuarioRq usuarioNuevo)
            throws BadRequestException {
        return ResponseEntity.ok(this.usuarioService.guardarUsuario(usuarioNuevo));
    }

}
=======


    @Override
    public ResponseEntity<RespuestaRs> guardarUsuario(UsuarioRq usuarioNuevo) 
            throws BadRequestException {
                return ResponseEntity.ok(this.usuarioService.guardarUsuario(usuarioNuevo));

    }

}
>>>>>>> 602c738be275f7f1826ccf9ef7bcb734aeea96d1
