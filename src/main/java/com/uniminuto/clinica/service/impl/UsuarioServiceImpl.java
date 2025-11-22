package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.entity.Usuario;
import com.uniminuto.clinica.model.UsuarioRq;
import com.uniminuto.clinica.model.RespuestaRs;
import com.uniminuto.clinica.repository.UsuarioRepository;
import com.uniminuto.clinica.service.UsuarioService;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${app.login.bloqueo.minutos:5}")
    private int minutosBloqueo;

    @Value("${app.login.intentos.maximos:3}")
    private int intentosMaximos;

    private static final Logger log = LoggerFactory.getLogger(UsuarioServiceImpl.class);

    @Override
    public List<Usuario> listarTodosLosUsuarios() {
        return usuarioRepository.findAll();
    }

    @Override
    public List<Usuario> encontrarPorRol(String rol) {
        return usuarioRepository.findByRol(rol.toUpperCase());
    }

    @Override
    public Usuario encontrarPorNombre(String nombreUsuario) {
        return usuarioRepository.findByUsername(nombreUsuario.toLowerCase())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuario no encontrado"));
    }

    @Override
    public List<Usuario> buscarPorEstado(Integer estado) {
        boolean activo = (estado != null && estado == 1);
        return usuarioRepository.findByActivo(activo);
    }

    @Override
    public RespuestaRs guardarUsuario(UsuarioRq usuarioNuevo) {
        if (usuarioRepository.existsByUsername(usuarioNuevo.getUsername().toLowerCase())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El usuario ya existe.");
        }

        Usuario nuevo = new Usuario();
        nuevo.setUsername(usuarioNuevo.getUsername().toLowerCase());
        nuevo.setFechaCreacion(LocalDateTime.now());
        nuevo.setRol(usuarioNuevo.getRol().toUpperCase());
        nuevo.setPassword(passwordEncoder.encode(usuarioNuevo.getPassword())); // ✅ Usando PasswordEncoder
        nuevo.setActivo(true);
        nuevo.setIntentosFallidos(0);
        nuevo.setBloqueadoHasta(null);

        usuarioRepository.save(nuevo);

        return new RespuestaRs(200, "Usuario creado con éxito");
    }

    @Override
    public RespuestaRs actualizarUsuario(UsuarioRq usuarioRq) {
        Usuario usuario = usuarioRepository.findById(usuarioRq.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuario no encontrado"));

        usuario.setUsername(usuarioRq.getUsername().toLowerCase());
        if (usuarioRq.getPassword() != null && !usuarioRq.getPassword().isEmpty()) {
            usuario.setPassword(passwordEncoder.encode(usuarioRq.getPassword())); // ✅ Actualiza hash con PasswordEncoder
        }
        usuario.setRol(usuarioRq.getRol().toUpperCase());
        usuario.setActivo(usuarioRq.isActivo()); // Cambiado de isActivo() a getActivo()

        usuarioRepository.save(usuario);
        return new RespuestaRs(200, "Usuario actualizado con éxito");
    }

    @Override
    public RespuestaRs recuperarPassword(String username) {
        Usuario usuario = usuarioRepository.findByUsername(username.toLowerCase())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Aquí podrías generar un password temporal
        usuario.setTempPasswordHash(passwordEncoder.encode("temporal123")); // ✅ temporal seguro
        usuario.setTempPasswordExpira(LocalDateTime.now().plusHours(1));
        usuarioRepository.save(usuario);

        return new RespuestaRs(200, "Se ha enviado un correo para restablecer la contraseña");
    }

    @Override
public void login(String username, String password, String ip) {

    Usuario usuario = usuarioRepository.findByUsername(username.toLowerCase())
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

    if (usuario.getBloqueadoHasta() != null && usuario.getBloqueadoHasta().isAfter(LocalDateTime.now())) {
        log.warn("Intento de login de usuario {} desde IP {} - USUARIO BLOQUEADO hasta {}", username, ip, usuario.getBloqueadoHasta());
        throw new RuntimeException("Usuario bloqueado hasta " + usuario.getBloqueadoHasta());
    }

    if (!passwordEncoder.matches(password, usuario.getPassword())) {
        usuario.setIntentosFallidos(usuario.getIntentosFallidos() + 1);

        if (usuario.getIntentosFallidos() >= intentosMaximos) {
            usuario.setBloqueadoHasta(LocalDateTime.now().plusMinutes(minutosBloqueo));
            usuario.setIntentosFallidos(0);
            log.warn("Usuario {} bloqueado temporalmente hasta {}", username, usuario.getBloqueadoHasta());
        } else {
            log.info("Intento fallido {} de {} para usuario {}", usuario.getIntentosFallidos(), intentosMaximos, username);
        }

        usuarioRepository.save(usuario);
        throw new RuntimeException("Credenciales inválidas");
    }

    usuario.setIntentosFallidos(0);
    usuario.setBloqueadoHasta(null);
    usuarioRepository.save(usuario);

    log.info("Usuario {} ha iniciado sesión correctamente desde IP {}", username, ip);
}
}

