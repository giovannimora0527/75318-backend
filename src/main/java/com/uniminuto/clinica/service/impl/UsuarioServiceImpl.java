package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.entity.Usuario;
import com.uniminuto.clinica.model.UsuarioRq;
import com.uniminuto.clinica.model.RespuestaRs;
import com.uniminuto.clinica.model.AuditLog;
import com.uniminuto.clinica.repository.UsuarioRepository;
import com.uniminuto.clinica.service.AuditLogService;
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

    @Autowired
    private AuditLogService auditLogService;

    @Value("${app.login.bloqueo.minutos:5}")
    private int minutosBloqueo;

    @Value("${app.login.intentos.maximos:3}")
    private int intentosMaximos;

    private static final Logger log = LoggerFactory.getLogger(UsuarioServiceImpl.class);

    // --------------------------------------
    // UTILIDAD PARA REGISTRAR AUDITORÍA
    // --------------------------------------
    private void registrarAuditoria(String username, String eventType, String descripcion, String ip) {
        AuditLog logEntry = new AuditLog();
        logEntry.setUsername(username);
        logEntry.setEventType(eventType);
        logEntry.setDescription(descripcion);
        logEntry.setTimestamp(LocalDateTime.now());
        logEntry.setIp(ip);
        auditLogService.saveLog(logEntry);
    }

    // --------------------------------------
    // SERVICIOS
    // --------------------------------------
    @Override
    public List<Usuario> listarTodosLosUsuarios() {
        return usuarioRepository.findAll();
    }

    @Override
    public List<Usuario> encontrarPorRol(String rol) {
        if (rol == null || rol.trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El rol no puede ser nulo");
        }
        return usuarioRepository.findByRol(rol.toUpperCase());
    }

    @Override
    public Usuario encontrarPorNombre(String nombreUsuario) {
        if (nombreUsuario == null || nombreUsuario.trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El username no puede ser nulo");
        }
        final String nombreFinal = nombreUsuario.toLowerCase();
        return usuarioRepository.findByUsername(nombreFinal)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuario no encontrado"));
    }

    @Override
    public List<Usuario> buscarPorEstado(Integer estado) {
        boolean activo = (estado != null && estado == 1);
        return usuarioRepository.findByActivo(activo);
    }

   @Override
public RespuestaRs guardarUsuario(UsuarioRq usuarioNuevo) {

    final String usernameLower = usuarioNuevo.getUsername().toLowerCase();

    if (usuarioRepository.existsByUsername(usernameLower)) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El usuario ya existe.");
    }

    Usuario nuevo = new Usuario();
    nuevo.setUsername(usernameLower);
    nuevo.setEmail(usuarioNuevo.getEmail()); // ✅ ahora seteamos email
    nuevo.setFechaCreacion(LocalDateTime.now());
    nuevo.setRol(usuarioNuevo.getRol().toUpperCase());
    nuevo.setPassword(passwordEncoder.encode(usuarioNuevo.getPassword()));
    nuevo.setActivo(true);
    nuevo.setIntentosFallidos(0);
    nuevo.setBloqueadoHasta(null);

    usuarioRepository.save(nuevo);

    registrarAuditoria(
            nuevo.getUsername(),
            "CREATE_USER",
            "Se creó un nuevo usuario",
            null
    );

    return new RespuestaRs(200, "Usuario creado con éxito");
}
    @Override
    public RespuestaRs actualizarUsuario(UsuarioRq usuarioRq) {
        if (usuarioRq == null || usuarioRq.getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuario o id no pueden ser nulos");
        }
        if (usuarioRq.getUsername() == null || usuarioRq.getUsername().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El username es obligatorio");
        }
        if (usuarioRq.getRol() == null || usuarioRq.getRol().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El rol es obligatorio");
        }

        Usuario usuario = usuarioRepository.findById(usuarioRq.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuario no encontrado"));

        usuario.setUsername(usuarioRq.getUsername().toLowerCase());
        usuario.setEmail(usuarioRq.getEmail());
        if (usuarioRq.getPassword() != null && !usuarioRq.getPassword().trim().isEmpty()) {
            usuario.setPassword(passwordEncoder.encode(usuarioRq.getPassword()));
        }
        usuario.setRol(usuarioRq.getRol().toUpperCase());
        usuario.setActivo(usuarioRq.isActivo());

        log.info("Actualizando usuario: {}", usuario.getUsername());

        usuarioRepository.save(usuario);

        registrarAuditoria(
                usuario.getUsername(),
                "UPDATE_USER",
                "El usuario fue actualizado",
                null
        );

        return new RespuestaRs(200, "Usuario actualizado con éxito");
    }

    @Override
    public RespuestaRs recuperarPassword(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("El username no puede estar vacío");
        }

        final String usernameFinal = username.toLowerCase();

        usuarioRepository.findByUsername(usernameFinal).ifPresent(usuario -> {
            usuario.setTempPasswordHash(passwordEncoder.encode("temporal123"));
            usuario.setTempPasswordExpira(LocalDateTime.now().plusHours(1));
            usuarioRepository.save(usuario);

            registrarAuditoria(
                    usernameFinal,
                    "RECOVERY_ATTEMPT",
                    "Intento de recuperación de contraseña",
                    null
            );
        });

        return new RespuestaRs(200, "Se ha enviado un correo para restablecer la contraseña");
    }

    // ==========================
    // LOGIN JWT
    // ==========================
    @Override
    public Usuario login(String username, String password, String ip) {
        final String ipFinal = (ip != null) ? ip : "N/A";
        final String usernameFinal = username.toLowerCase();

        Usuario usuario = usuarioRepository.findByUsername(usernameFinal)
                .orElseThrow(() -> {
                    registrarAuditoria(usernameFinal, "LOGIN_FAIL", "Usuario no encontrado", ipFinal);
                    return new RuntimeException("Usuario no encontrado");
                });

        if (usuario.getBloqueadoHasta() != null && usuario.getBloqueadoHasta().isAfter(LocalDateTime.now())) {
            registrarAuditoria(usernameFinal, "LOGIN_BLOCKED", "Intento de login con usuario bloqueado", ipFinal);
            throw new RuntimeException("Usuario bloqueado hasta " + usuario.getBloqueadoHasta());
        }

        if (!passwordEncoder.matches(password, usuario.getPassword())) {
            usuario.setIntentosFallidos(usuario.getIntentosFallidos() + 1);
            if (usuario.getIntentosFallidos() >= intentosMaximos) {
                usuario.setBloqueadoHasta(LocalDateTime.now().plusMinutes(minutosBloqueo));
                usuario.setIntentosFallidos(0);
                registrarAuditoria(usernameFinal, "LOGIN_BLOCKED",
                        "Usuario bloqueado por superar los intentos máximos", ipFinal);
            } else {
                registrarAuditoria(usernameFinal, "LOGIN_FAIL", "Contraseña incorrecta", ipFinal);
            }
            usuarioRepository.save(usuario);
            throw new RuntimeException("Credenciales inválidas");
        }

        // Login exitoso
        usuario.setIntentosFallidos(0);
        usuario.setBloqueadoHasta(null);
        usuarioRepository.save(usuario);

        registrarAuditoria(usernameFinal, "LOGIN_SUCCESS", "Inicio de sesión exitoso", ipFinal);

        return usuario;
    }
}
