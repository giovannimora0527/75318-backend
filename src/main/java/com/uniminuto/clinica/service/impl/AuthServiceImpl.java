package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.entity.AuditLog;
import com.uniminuto.clinica.entity.Usuario;
import com.uniminuto.clinica.repository.AuditLogRepository;
import com.uniminuto.clinica.repository.UsuarioRepository;
import com.uniminuto.clinica.service.AuthService;
import com.uniminuto.clinica.service.CifrarService;
import com.uniminuto.clinica.service.EmailService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class AuthServiceImpl implements AuthService {

    private static final int MAX_ATTEMPTS = 3;
    private static final int LOCK_MINUTES = 5;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AuditLogRepository auditRepo;

    @Autowired
    private CifrarService cifrarService;

    @Autowired
    private EmailService emailService;

    @Override
    public void recordLoginFailure(String usernameInput, String ipAddress) {

        auditRepo.save(new AuditLog(
                LocalDateTime.now(),
                usernameInput,
                AuditLog.EventType.LOGIN_FAIL,
                "Intento de login fallido",
                ipAddress
        ));

        Optional<Usuario> usuarioOpt = usuarioRepository.findByUsername(usernameInput);
        if (usuarioOpt.isEmpty()) return;

        Usuario usuario = usuarioOpt.get();

        int attempts = usuario.getFailedAttempts() + 1;
        usuario.setFailedAttempts(attempts);

        if (attempts >= MAX_ATTEMPTS) {
            usuario.setLockedUntil(LocalDateTime.now().plusMinutes(LOCK_MINUTES));

            auditRepo.save(new AuditLog(
                    LocalDateTime.now(),
                    usernameInput,
                    AuditLog.EventType.LOCK,
                    "Usuario bloqueado por múltiples intentos fallidos",
                    ipAddress
            ));
        }

        usuarioRepository.save(usuario);
    }

    @Override
    public void recordLoginSuccess(String usernameInput, String ipAddress) {

        Optional<Usuario> usuarioOpt = usuarioRepository.findByUsername(usernameInput);
        if (usuarioOpt.isEmpty()) return;

        Usuario usuario = usuarioOpt.get();

        usuario.setFailedAttempts(0);
        usuario.setLockedUntil(null);
        usuarioRepository.save(usuario);

        auditRepo.save(new AuditLog(
                LocalDateTime.now(),
                usernameInput,
                AuditLog.EventType.LOGIN_SUCCESS,
                "Inicio de sesión exitoso",
                ipAddress
        ));
    }

    @Override
    public boolean isLocked(Usuario usuario) {
        return usuario.getLockedUntil() != null &&
                usuario.getLockedUntil().isAfter(LocalDateTime.now());
    }

    @Override
    public String recoverPassword(String usernameInput, String ipAddress) throws MessagingException {

        Optional<Usuario> usuarioOpt = usuarioRepository.findByUsername(usernameInput);

        auditRepo.save(new AuditLog(
                LocalDateTime.now(),
                usernameInput,
                AuditLog.EventType.RECOVERY,
                usuarioOpt.isPresent() ?
                        "Solicitud de recuperación enviada" :
                        "Solicitud de recuperación para usuario inexistente",
                ipAddress
        ));

        if (usuarioOpt.isEmpty()) return null;

        Usuario usuario = usuarioOpt.get();

        // Generar password temporal
        String tempPassword = generateTempPassword();

        usuario.setTempPasswordHash(
                cifrarService.encriptarPassword(tempPassword)
        );
        // 🔥🔥🔥🔥Tiempo de expiracion para la contraseña temporal🔥🔥🔥🔥
        usuario.setTempPasswordExpiration(
                LocalDateTime.now().plusMinutes(1)
        );

        usuarioRepository.save(usuario);

        // 🔥 TRATAR BadRequestException AQUÍ
        try {
            emailService.enviarCorreoSimple(
                    usuario.getEmail(),
                    "Recuperación de contraseña",
                    "Su contraseña temporal es: " + tempPassword
            );
        } catch (BadRequestException e) {
            System.err.println("❌ Error enviando correo (BadRequestException): " + e.getMessage());
            // opcionalmente loguearlo
        }

        return tempPassword;
    }

    private String generateTempPassword() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random rand = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            sb.append(chars.charAt(rand.nextInt(chars.length())));
        }
        return sb.toString();
    }
}
