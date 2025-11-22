package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.entity.Usuario;
import com.uniminuto.clinica.entity.Session;
import com.uniminuto.clinica.model.AutenticatorRs;
import com.uniminuto.clinica.model.AuthenticatorRq;
import com.uniminuto.clinica.model.ChangePasswordRq;
import com.uniminuto.clinica.model.RespuestaRs;
import com.uniminuto.clinica.repository.UsuarioRepository;
import com.uniminuto.clinica.repository.SessionRepository;
import com.uniminuto.clinica.security.JwtUtil;
import com.uniminuto.clinica.service.AutenticarService;
import com.uniminuto.clinica.service.AuthService;
import com.uniminuto.clinica.service.CifrarService;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Service
public class AutenticarServiceImpl implements AutenticarService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired(required = false)
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CifrarService cifrarService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private AuthService authService;

    @Override
    @Transactional
    public AutenticatorRs autenticar(AuthenticatorRq request)
            throws BadRequestException {

        String username = request.getUsername().toLowerCase();
        String password = request.getPassword();
        String ip = "0.0.0.0";

        Optional<Usuario> usuarioOpt = usuarioRepository.findByUsername(username);

        if (usuarioOpt.isEmpty()) {
            authService.recordLoginFailure(username, ip);
            throw new BadRequestException("Usuario o contraseña incorrectos");
        }

        Usuario usuario = usuarioOpt.get();

        if (authService.isLocked(usuario)) {
            authService.recordLoginFailure(username, ip);
            throw new BadRequestException("Usuario o contraseña incorrectos");
        }

        String passwordHash = cifrarService.encriptarPassword(password);
        boolean passwordOK = false;
        boolean usedTempPassword = false;

        // 1️⃣ CONTRASEÑA NORMAL
        if (usuario.getPassword() != null &&
                usuario.getPassword().equals(passwordHash)) {
            passwordOK = true;
        }

        // 2️⃣ CONTRASEÑA TEMPORAL
        if (!passwordOK && usuario.getTempPasswordHash() != null) {

            boolean tempCorrecta =
                    usuario.getTempPasswordHash().equals(passwordHash);

            boolean tempVigente =
                    usuario.getTempPasswordExpiration() != null &&
                            usuario.getTempPasswordExpiration().isAfter(LocalDateTime.now());

            // ❌ TEMPORAL CORRECTA PERO expirada
            if (tempCorrecta && !tempVigente) {
                throw new BadRequestException(
                        "La contraseña temporal ha expirado. Solicite una nueva."
                );
            }

            // ✔ TEMPORAL CORRECTA Y VIGENTE
            if (tempCorrecta && tempVigente) {
                passwordOK = true;
                usedTempPassword = true;

                // Invalidar temporal (se usó correctamente)
                usuario.setTempPasswordHash(null);
                usuario.setTempPasswordExpiration(null);

                // Importante: la contraseña REAL AHORA pasa a ser la temporal
                usuario.setPassword(passwordHash);

                usuarioRepository.save(usuario);
            }
        }

        // 3️⃣ NINGUNA CONTRASEÑA FUE VÁLIDA
        if (!passwordOK) {
            authService.recordLoginFailure(username, ip);
            throw new BadRequestException("Usuario o contraseña incorrectos");
        }

        // 4️⃣ LOGIN OK
        authService.recordLoginSuccess(username, ip);

        AutenticatorRs rta = new AutenticatorRs();

        // 🔥 GENERAR TOKEN SEGÚN SI USÓ TEMPORAL
        String token = jwtUtil.generateToken(usuario, usedTempPassword);
        rta.setToken(token);
        rta.setRequiereCambioPassword(usedTempPassword);

        crearSesionUsuario(usuario, token);

        return rta;
    }

    private void crearSesionUsuario(Usuario usuario, String token) {

        sessionRepository.deleteByUserId(usuario.getId().intValue());

        Session session = new Session();
        session.setUserId(usuario.getId().intValue());
        session.setToken(token);
        session.setFechaIniSesion(LocalDateTime.now());

        Date fechaExpiracion = jwtUtil.getExpirationDateFromToken(token);
        session.setFechaExpiracion(
                fechaExpiracion.toInstant()
                        .atZone(java.time.ZoneId.systemDefault())
                        .toLocalDateTime()
        );

        sessionRepository.save(session);
    }

    @Override
    @Transactional
    public RespuestaRs cambiarPassword(String username, ChangePasswordRq request)
            throws BadRequestException {

        Optional<Usuario> usuarioOpt = usuarioRepository.findByUsername(username);

        if (usuarioOpt.isEmpty()) {
            throw new BadRequestException("Usuario no encontrado");
        }

        Usuario usuario = usuarioOpt.get();

        String passActualHash = cifrarService.encriptarPassword(request.getPasswordActual());

        // 1️⃣ Validar contraseña actual
        boolean coincideActual = usuario.getPassword() != null &&
                usuario.getPassword().equals(passActualHash);

        if (!coincideActual) {
            throw new BadRequestException("La contraseña actual es incorrecta");
        }

        // 2️⃣ Validar nueva contraseña (mínimo 6 caracteres)
        if (request.getPasswordNueva().length() < 6) {
            throw new BadRequestException("La nueva contraseña debe tener al menos 6 caracteres");
        }

        // 3️⃣ Hash de la nueva contraseña
        String nuevaHash = cifrarService.encriptarPassword(request.getPasswordNueva());

        // 4️⃣ Actualizar
        usuario.setPassword(nuevaHash);
        usuario.setTempPasswordHash(null);
        usuario.setTempPasswordExpiration(null);

        usuarioRepository.save(usuario);

        // 5️⃣ Respuesta
        RespuestaRs res = new RespuestaRs();
        res.setMensaje("Contraseña actualizada exitosamente");
        return res;
    }

    @Override
    public Long obtenerIdDesdeToken(String token) {
        try {
            String username = jwtUtil.extractUsername(token);
            return usuarioRepository.findByUsername(username)
                    .map(Usuario::getId)
                    .orElse(null);
        } catch (Exception e) {
            return null;
        }
    }
}
