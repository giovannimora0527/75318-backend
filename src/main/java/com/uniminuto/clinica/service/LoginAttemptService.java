package com.uniminuto.clinica.service;

import com.uniminuto.clinica.config.LoginSecurityConfig;
import com.uniminuto.clinica.entity.Usuario;
import com.uniminuto.clinica.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginAttemptService {

    private final UsuarioRepository usuarioRepository;
    private final LoginSecurityConfig loginConfig;
    private final AuditoriaService auditoriaService;

    @Transactional
    public void registrarIntentoFallido(String username, String ipAddress, String userAgent) {
        try {
            // Registrar en auditoría
            auditoriaService.registrarEvento(
                    username,
                    "LOGIN_FALLIDO",
                    false,
                    "Credenciales incorrectas",
                    ipAddress,
                    userAgent
            );

            var usuarioOpt = usuarioRepository.findByUsername(username);
            if (usuarioOpt.isPresent()) {
                Usuario usuario = usuarioOpt.get();
                
                if (Boolean.TRUE.equals(usuario.getCuentaBloqueada())) {
                    log.warn("Intento de login con cuenta bloqueada - Usuario: {}, IP: {}", username, ipAddress);
                    return;
                }

                int nuevosIntentos = (usuario.getIntentosFallidos() != null ? usuario.getIntentosFallidos() : 0) + 1;
                usuario.setIntentosFallidos(nuevosIntentos);

                log.info("Intento fallido {} para usuario: {}, IP: {}", nuevosIntentos, username, ipAddress);

                if (nuevosIntentos >= loginConfig.getMaxIntentosFallidos()) {
                    bloquearUsuario(usuario, ipAddress);
                } else {
                    usuarioRepository.save(usuario);
                }
            }
        } catch (Exception e) {
            log.error("Error al registrar intento fallido para usuario: {}", username, e);
        }
    }

    private void bloquearUsuario(Usuario usuario, String ipAddress) {
        usuario.setCuentaBloqueada(true);
        usuario.setFechaBloqueo(LocalDateTime.now());
        usuarioRepository.save(usuario);
        
        log.warn("🚨 USUARIO BLOQUEADO - Usuario: {}, IP: {}, Fecha: {}", 
                usuario.getUsername(), ipAddress, LocalDateTime.now());
        
        auditoriaService.registrarEvento(
                usuario.getUsername(),
                "CUENTA_BLOQUEADA",
                false,
                "Cuenta bloqueada por exceso de intentos fallidos",
                ipAddress,
                "Sistema"
        );
    }

    @Transactional
    public void resetearIntentosFallidos(String username) {
        try {
            var usuarioOpt = usuarioRepository.findByUsername(username);
            if (usuarioOpt.isPresent()) {
                Usuario usuario = usuarioOpt.get();
                usuario.setIntentosFallidos(0);
                usuario.setCuentaBloqueada(false);
                usuario.setFechaBloqueo(null);
                usuarioRepository.save(usuario);
                
                log.info("Intentos fallidos reseteados para usuario: {}", username);
            }
        } catch (Exception e) {
            log.error("Error al resetear intentos fallidos para usuario: {}", username, e);
        }
    }

    public boolean estaBloqueado(String username) {
        try {
            var usuarioOpt = usuarioRepository.findByUsername(username);
            if (usuarioOpt.isPresent()) {
                Usuario usuario = usuarioOpt.get();
                
                if (Boolean.TRUE.equals(usuario.getCuentaBloqueada()) && usuario.getFechaBloqueo() != null) {
                    LocalDateTime finBloqueo = usuario.getFechaBloqueo().plusMinutes(loginConfig.getMinutosBloqueo());
                    
                    if (LocalDateTime.now().isAfter(finBloqueo)) {
                        desbloquearUsuario(usuario);
                        return false;
                    }
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            log.error("Error al verificar bloqueo para usuario: {}", username, e);
            return false;
        }
    }

    @Transactional
    private void desbloquearUsuario(Usuario usuario) {
        usuario.setCuentaBloqueada(false);
        usuario.setIntentosFallidos(0);
        usuario.setFechaBloqueo(null);
        usuarioRepository.save(usuario);
        
        log.info("✅ USUARIO DESBLOQUEADO AUTOMÁTICAMENTE - Usuario: {}", usuario.getUsername());
        
        auditoriaService.registrarEvento(
                usuario.getUsername(),
                "CUENTA_DESBLOQUEADA",
                true,
                "Cuenta desbloqueada automáticamente",
                "Sistema",
                "Sistema"
        );
    }

    public String obtenerTiempoRestanteBloqueo(String username) {
        var usuarioOpt = usuarioRepository.findByUsername(username);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            if (Boolean.TRUE.equals(usuario.getCuentaBloqueada()) && usuario.getFechaBloqueo() != null) {
                LocalDateTime finBloqueo = usuario.getFechaBloqueo().plusMinutes(loginConfig.getMinutosBloqueo());
                long minutosRestantes = ChronoUnit.MINUTES.between(LocalDateTime.now(), finBloqueo);
                return minutosRestantes + " minutos";
            }
        }
        return "0 minutos";
    }
}