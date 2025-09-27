package com.uniminuto.clinica.service;

import com.uniminuto.clinica.entity.Usuario;
import com.uniminuto.clinica.model.RespuestaRs;
import com.uniminuto.clinica.model.UsuarioRq;
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
      //Encontrar por ID usuario

  Usuario encontrarPorId(Long id) throws BadRequestException;

=======
 RespuestaRs guardarUsuario(UsuarioRq usuarioNuevo) throws BadRequestException;
>>>>>>> 602c738be275f7f1826ccf9ef7bcb734aeea96d1
}
