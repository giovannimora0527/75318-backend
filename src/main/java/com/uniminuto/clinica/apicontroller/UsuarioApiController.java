package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.api.UsuarioApi;
import com.uniminuto.clinica.entity.Usuario;
import com.uniminuto.clinica.model.RespuestaRs;
import com.uniminuto.clinica.model.UsuarioRq;
import com.uniminuto.clinica.service.AuditoriaService;
import com.uniminuto.clinica.service.UsuarioService;
import com.uniminuto.clinica.utils.SecurityUtils;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author lmora
 */
@RestController
public class UsuarioApiController implements UsuarioApi {

    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private AuditoriaService auditoriaService;

    @Override
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        try {
            return ResponseEntity.ok(this.usuarioService.listarTodosLosUsuarios());
        } catch (Exception e) {
            System.err.println("Error al listar usuarios: " + e.getMessage());
            e.printStackTrace();
            throw e; // Re-lanzar para que Spring maneje el error
        }
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

    @Override
    public ResponseEntity<List<Usuario>> buscarUsuariosPorEstado(Integer activo)
            throws BadRequestException {
        return ResponseEntity.ok(this.usuarioService.buscarPorEstado(activo));
    }

    @Override
    public ResponseEntity<RespuestaRs> guardarUsuario(UsuarioRq usuarioNuevo, HttpServletRequest request)
            throws BadRequestException, MessagingException {
        RespuestaRs respuesta = this.usuarioService.guardarUsuario(usuarioNuevo);
        
        // Registrar auditoría solo si la operación fue exitosa
        try {
            if (respuesta != null && respuesta.getStatus() == 200) {
                String username = SecurityUtils.getCurrentUsername();
                HttpServletRequest httpRequest = request;
                if (httpRequest == null) {
                    ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
                    httpRequest = attributes.getRequest();
                }
                String descripcion = String.format("Se creó el usuario: %s", usuarioNuevo.getUsername());
                auditoriaService.registrarCrear(username != null ? username : "SISTEMA", "USUARIO", descripcion, httpRequest);
            }
        } catch (Exception e) {
            System.err.println("ERROR al registrar auditoría de creación de usuario: " + e.getMessage());
            e.printStackTrace();
        }
        
        return ResponseEntity.ok(respuesta);
    }

    @Override
    public ResponseEntity<RespuestaRs> actualizarrUsuario(UsuarioRq usuario, HttpServletRequest request)
            throws BadRequestException {
        RespuestaRs respuesta = this.usuarioService.actualizarUsuario(usuario);
        
        // Registrar auditoría solo si la operación fue exitosa
        try {
            if (respuesta != null && respuesta.getStatus() == 200) {
                String username = SecurityUtils.getCurrentUsername();
                HttpServletRequest httpRequest = request;
                if (httpRequest == null) {
                    ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
                    httpRequest = attributes.getRequest();
                }
                String descripcion = String.format("Se actualizó el usuario: %s (ID: %s)", usuario.getUsername(), usuario.getId() != null ? usuario.getId() : "N/A");
                auditoriaService.registrarActualizar(username != null ? username : "SISTEMA", "USUARIO", descripcion, httpRequest);
            }
        } catch (Exception e) {
            System.err.println("ERROR al registrar auditoría de actualización de usuario: " + e.getMessage());
            e.printStackTrace();
        }
        
        return ResponseEntity.ok(respuesta);
    }

}
