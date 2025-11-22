package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.entity.Usuario;
import com.uniminuto.clinica.model.RespuestaRs;
import com.uniminuto.clinica.model.UsuarioRq;
import com.uniminuto.clinica.repository.UsuarioRepository;
import com.uniminuto.clinica.service.AuditoriaService;
import com.uniminuto.clinica.service.UsuarioService;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

/**
 *
 * @author lmora
 */
@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    @Lazy
    private AuditoriaService auditoriaService;

    @Override
    public List<Usuario> listarTodosLosUsuarios() {
        return usuarioRepository.findAll();
    }

    @Override
    public List<Usuario> encontrarPorRol(String rol) {
        return usuarioRepository.findByRol(rol);
    }

    @Override
    public Usuario encontrarPorNombre(String nombreUsuario)
            throws BadRequestException {

        return usuarioRepository.findByUsername(nombreUsuario)
                .orElseThrow(() -> new BadRequestException("No existe el usuario"));
    }

    @Override
    public List<Usuario> buscarPorEstado(Integer estado) {
        boolean activo = estado == 1;
        return usuarioRepository.findByActivo(activo);
    }

    @Override
    public RespuestaRs guardarUsuario(UsuarioRq usuarioNuevo)
            throws BadRequestException {

        validarCampos(usuarioNuevo);

        Optional<Usuario> optUser =
                usuarioRepository.findByUsername(usuarioNuevo.getUsername().toLowerCase());

        if (optUser.isPresent()) {
            throw new BadRequestException("El usuario ya existe.");
        }

        Usuario nuevo = new Usuario();
        nuevo.setActivo(true);
        nuevo.setFechaCreacion(LocalDateTime.now());
        nuevo.setPassword(encriptarPassword(usuarioNuevo.getPassword()));
        nuevo.setRol(usuarioNuevo.getRol().toUpperCase());
        nuevo.setUsername(usuarioNuevo.getUsername().toLowerCase());
        nuevo.setEmail(usuarioNuevo.getEmail());

        // GUARDAR Y GENERAR ID
        nuevo = usuarioRepository.save(nuevo);

        // AUDITORÍA - INSERT
        auditoriaService.registrarAuditoria(
                "usuario",
                nuevo.getId(),
                "INSERT",
                null,
                nuevo,
                "Usuario creado exitosamente"
        );

        RespuestaRs rta = new RespuestaRs();
        rta.setMensaje("Usuario guardado correctamente.");
        rta.setStatus(200);
        return rta;
    }

    @Override
    public RespuestaRs actualizarUsuario(UsuarioRq usuarioRq) throws BadRequestException {

        Optional<Usuario> optUser = usuarioRepository.findById(usuarioRq.getId());
        if (optUser.isEmpty()) {
            throw new BadRequestException("El ID del usuario no existe.");
        }

        Usuario userActualizar = optUser.get();

        String valoresAntes = userActualizar.toString();

        // Validar username repetido
        if (!userActualizar.getUsername().equals(usuarioRq.getUsername().toLowerCase())) {
            Optional<Usuario> optUsername = usuarioRepository.findByUsername(usuarioRq.getUsername().toLowerCase());
            if (optUsername.isPresent()) {
                throw new BadRequestException("El username ya existe.");
            }
        }

        validarCampos(usuarioRq);

        userActualizar.setUsername(usuarioRq.getUsername().toLowerCase());
        userActualizar.setPassword(encriptarPassword(usuarioRq.getPassword()));
        userActualizar.setActivo(usuarioRq.isActivo());
        userActualizar.setRol(usuarioRq.getRol());

        Usuario actualizado = usuarioRepository.save(userActualizar);

        // Auditoría
        auditoriaService.registrarAuditoria(
                "usuario",
                actualizado.getId(),
                "UPDATE",
                valoresAntes,
                actualizado.toString(),
                "Usuario actualizado correctamente"
        );

        RespuestaRs rta = new RespuestaRs();
        rta.setMensaje("Usuario actualizado correctamente.");
        rta.setStatus(200);
        return rta;
    }

    @Override
    public RespuestaRs eliminarUsuario(Long idUsuario) throws BadRequestException {

        Optional<Usuario> optUser = usuarioRepository.findById(idUsuario);

        if (optUser.isEmpty()) {
            throw new BadRequestException("El usuario no existe, no se puede eliminar");
        }

        Usuario usuario = optUser.get();

        // Valores antes
        String valoresAntes = usuario.toString();

        // Eliminar
        usuarioRepository.delete(usuario);

        // Auditoría
        auditoriaService.registrarAuditoria(
                "usuario",
                usuario.getId(),
                "DELETE",
                valoresAntes,
                null,
                "Usuario eliminado"
        );

        RespuestaRs rta = new RespuestaRs();
        rta.setMensaje("Usuario eliminado correctamente");
        rta.setStatus(200);

        return rta;
    }

    private void validarCampos(UsuarioRq usuarioNuevo)
            throws BadRequestException {

        if (usuarioNuevo.getUsername() == null || usuarioNuevo.getUsername().isBlank()) {
            throw new BadRequestException("El campo username es obligatorio.");
        }
        if (usuarioNuevo.getPassword() == null || usuarioNuevo.getPassword().isBlank()) {
            throw new BadRequestException("El campo password es obligatorio.");
        }
        if (usuarioNuevo.getRol() == null || usuarioNuevo.getRol().isBlank()) {
            throw new BadRequestException("El campo rol es obligatorio.");
        }
    }

    private String encriptarPassword(String passAEncriptar) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hashBytes = md.digest(passAEncriptar.getBytes());

            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error encriptando password", e);
        }
    }

    @Override
    public Usuario getUsuarioLogeado() {
        return usuarioRepository.findById(1L).orElse(null);
    }

    public Usuario login(String username, String password) {
        Optional<Usuario> optionalUsuario = usuarioRepository.findByUsername(username);
        if (optionalUsuario.isEmpty()) {
            return null; // Usuario no encontrado
        }

        Usuario usuario = optionalUsuario.get();

        // Verificar si el usuario está bloqueado
        if (usuario.getBloqueadoHasta() != null && usuario.getBloqueadoHasta().isAfter(LocalDateTime.now())) {
            throw new RuntimeException("Usuario bloqueado hasta " + usuario.getBloqueadoHasta());
        }

        // Validar contraseña
        if (!BCrypt.checkpw(password, usuario.getPassword())) {
            usuario.setIntentosFallidos(usuario.getIntentosFallidos() + 1);
            usuarioRepository.save(usuario);
            return null;
        }

        // Resetear intentos fallidos
        usuario.setIntentosFallidos(0);
        usuarioRepository.save(usuario);

        return usuario;
    }

}
