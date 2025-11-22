package com.uniminuto.clinica.service.impl;


import com.uniminuto.clinica.entity.PasswordResetToken;
import com.uniminuto.clinica.entity.Usuario;
import com.uniminuto.clinica.repository.PasswordResetTokenRepository;
import com.uniminuto.clinica.repository.UsuarioRepository;
import com.uniminuto.clinica.service.AuditoriaService;
import com.uniminuto.clinica.service.EmailService;
import com.uniminuto.clinica.service.PasswordResetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j

public class PasswordResetServiceImpl implements PasswordResetService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordResetTokenRepository tokenRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private final AuditoriaService auditoriaService;

    @Override
    public void enviarToken(String username) {

        // Por seguridad nunca mostramos si existe o no
        Optional<Usuario> optionalUser = usuarioRepository.findByUsername(username);

        if (optionalUser.isEmpty()) {
            // Registrar auditoría de intento de recuperación inválido
            auditoriaService.registrarAuditoriaExterna(
                    username,
                    "password_reset_token",
                    null,
                    "RECOVERY_REQUEST_INVALID",
                    null,
                    null,
                    "Intento de recuperación de contraseña con usuario inexistente"
            );
            return;
        }

        Usuario user = optionalUser.get();

        // Generar token
        String token = UUID.randomUUID().toString();

        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setUser(user);
        resetToken.setToken(token);
        resetToken.setExpiration(LocalDateTime.now().plusMinutes(30));
        resetToken.setUsed(false);

        tokenRepository.save(resetToken);

        // Enlace para el frontend
        String link = "https://tu-frontend.com/reset-password?token=" + token;

        try {
            emailService.enviarCorreo(
                    user.getEmail(),
                    "Recuperación de contraseña",
                    "Para recuperar tu contraseña ingresa al siguiente enlace:\n\n" + link
            );
        } catch (Exception e) {
            log.error("Error enviando correo de recuperación", e);
        }

        // Auditoría
        auditoriaService.registrarAuditoriaExterna(
                user.getUsername(),
                "password_reset_token",
                resetToken.getId().intValue(),
                "TOKEN_GENERADO",
                null,
                link,
                "Generación de token de recuperación"
        );
    }

    @Override
    public boolean validarToken(String token) {

        boolean valido = tokenRepository.findByToken(token)
                .filter(t -> !Boolean.TRUE.equals(t.getUsed()))
                .filter(t -> t.getExpiration().isAfter(LocalDateTime.now()))
                .isPresent();

        // Auditoría básica
        auditoriaService.registrarAuditoriaExterna(
                "desconocido",
                "password_reset_token",
                null,
                "TOKEN_VALIDADO",
                token,
                valido,
                "Validación de token"
        );

        return valido;
    }

    @Override
    public void actualizarPassword(String token, String nuevaPassword) {

        PasswordResetToken resetToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Token inválido"));

        if (resetToken.getUsed()) {
            throw new RuntimeException("Token ya usado");
        }

        Usuario user = resetToken.getUser();
        String oldPasswordHash = user.getPassword();

        // Actualizar contraseña
        user.setPassword(passwordEncoder.encode(nuevaPassword));
        resetToken.setUsed(true);

        usuarioRepository.save(user);
        tokenRepository.save(resetToken);

        // Auditoría
        auditoriaService.registrarAuditoriaExterna(
                user.getUsername(),
                "usuario",
                user.getId().intValue(),
                "PASSWORD_UPDATE",
                oldPasswordHash,
                "Nueva contraseña asignada",
                "Actualización de contraseña mediante token válido"
        );
    }
}

