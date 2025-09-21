package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.api.UsuarioApi;
import com.uniminuto.clinica.entity.Usuario;
<<<<<<< HEAD
import com.uniminuto.clinica.model.RespuestaRs;
import com.uniminuto.clinica.model.UsuarioRq;
=======
>>>>>>> 6ddb292738158152aa065f4d15449b4ce3a7c0c8
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
<<<<<<< HEAD
    public ResponseEntity<Usuario> buscarUsuarioPorNombre(String nombre)
            throws BadRequestException {
=======
    public ResponseEntity<Usuario> buscarUsuarioPorNombre(String nombre) 
            throws BadRequestException{
>>>>>>> 6ddb292738158152aa065f4d15449b4ce3a7c0c8
        return ResponseEntity.ok(this.usuarioService.encontrarPorNombre(nombre));
    }

    @Override
<<<<<<< HEAD
    public ResponseEntity<List<Usuario>> buscarUsuariosPorEstado(Integer activo)
            throws BadRequestException {
        return ResponseEntity.ok(this.usuarioService.buscarPorEstado(activo));
    }

    @Override
    public ResponseEntity<RespuestaRs> guardarUsuario(UsuarioRq usuarioNuevo)
            throws BadRequestException {
        return ResponseEntity.ok(this.usuarioService.guardarUsuario(usuarioNuevo));
=======
    public ResponseEntity<List<Usuario>> buscarUsuariosPorEstado(Integer activo) 
            throws BadRequestException {
        return ResponseEntity.ok(this
                .usuarioService.buscarPorEstado(activo));
>>>>>>> 6ddb292738158152aa065f4d15449b4ce3a7c0c8
    }

}
