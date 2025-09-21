package com.uniminuto.clinica.service;

import com.uniminuto.clinica.entity.Usuario;
<<<<<<< HEAD
import com.uniminuto.clinica.model.RespuestaRs;
import com.uniminuto.clinica.model.UsuarioRq;
=======
>>>>>>> 6ddb292738158152aa065f4d15449b4ce3a7c0c8
import java.util.List;
import org.apache.coyote.BadRequestException;

/**
 *
 * @author lmora
 */
public interface UsuarioService {
  List<Usuario> listarTodosLosUsuarios();
  
  List<Usuario> encontrarPorRol(String rol);
  
  Usuario encontrarPorNombre(String nombreUsuario) throws BadRequestException;
  
  List<Usuario> buscarPorEstado(Integer estado);
<<<<<<< HEAD
  
  RespuestaRs guardarUsuario(UsuarioRq usuarioNuevo) throws BadRequestException;
=======
>>>>>>> 6ddb292738158152aa065f4d15449b4ce3a7c0c8
}
