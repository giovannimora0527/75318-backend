package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.api.UsuarioApi;
import com.uniminuto.clinica.entity.Usuario;
import com.uniminuto.clinica.model.PacienteRq;
import com.uniminuto.clinica.model.RespuestaRs;
import com.uniminuto.clinica.model.UsuarioRq;
import com.uniminuto.clinica.service.UsuarioService;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import static com.uniminuto.clinica.security.RoleChecker.checkRole;

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
        checkRole();
        return ResponseEntity.ok(this.usuarioService.listarTodosLosUsuarios());
    }

    @Override
    public ResponseEntity<List<Usuario>> listarUsuariosPorRol(String rol) {
        checkRole();
        return ResponseEntity.ok(this.usuarioService.encontrarPorRol(rol));
    }

    @Override
    public ResponseEntity<Usuario> buscarUsuarioPorNombre(String nombre)
            throws BadRequestException {
        checkRole();
        return ResponseEntity.ok(this.usuarioService.encontrarPorNombre(nombre));
    }

    @Override
    public ResponseEntity<List<Usuario>> buscarUsuariosPorEstado(Integer activo)
            throws BadRequestException {
        checkRole();
        return ResponseEntity.ok(this.usuarioService.buscarPorEstado(activo));
    }

    @Override
    public ResponseEntity<RespuestaRs> guardarUsuario(UsuarioRq usuarioNuevo)
            throws BadRequestException {
        checkRole("ADMINISTRADOR");
        return ResponseEntity.ok(this.usuarioService.guardarUsuario(usuarioNuevo));
    }

    @Override
    public ResponseEntity<RespuestaRs> actualizarrUsuario(UsuarioRq usuario)
            throws BadRequestException {
        checkRole("ADMINISTRADOR");
        return ResponseEntity.ok(this.usuarioService.actualizarUsuario(usuario));
    }

    @Override
    public ResponseEntity<RespuestaRs> eliminarUsuario(Long idUsuario) throws BadRequestException {
        checkRole("ADMINISTRADOR");
        return ResponseEntity.ok(this.usuarioService.eliminarUsuario(idUsuario));
    }
}
