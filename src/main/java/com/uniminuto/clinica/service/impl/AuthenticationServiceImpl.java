package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.entity.Usuario;
import com.uniminuto.clinica.repository.UsuarioRepository;
import com.uniminuto.clinica.service.AuditoriaService;
import com.uniminuto.clinica.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UsuarioRepository usuarioRepository;
    private final AuditoriaService auditoriaService;

    @Value("${seguridad.tiempo-bloqueo-minutos}")
    private int tiempoBloqueoMinutos;

    @Value("${seguridad.intentos-maximos}")
    private int intentosMaximos;

    @Override
    public void login(String username, String password) {

        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElse(null);

        // --- Validar usuario inexistente ---
        if (usuario == null) {
            auditoriaService.registrarAuditoria(
                    "usuario",
                    null,
                    "LOGIN_FAIL",
                    null,
                    null,
                    "Intento de login fallido. Usuario no existe: " + username
            );
            throw new RuntimeException("Credenciales inválidas");
        }

        // --- Validar si está bloqueado ---
        if (usuario.getBloqueadoHasta() != null &&
                usuario.getBloqueadoHasta().isAfter(LocalDateTime.now())) {

            auditoriaService.registrarAuditoria(
                    "usuario",
                    usuario.getId().intValue(),
                    "LOGIN_BLOQUEADO",
                    null,
                    null,
                    "Usuario bloqueado hasta: " + usuario.getBloqueadoHasta()
            );

            throw new RuntimeException("Usuario temporalmente bloqueado.");
        }

        // --- Validar contraseña ---
        if (!password.equals(usuario.getPassword())) {

            usuario.setIntentosFallidos(usuario.getIntentosFallidos() + 1);

            auditoriaService.registrarAuditoria(
                    "usuario",
                    usuario.getId().intValue(),
                    "LOGIN_FAIL",
                    usuario.getIntentosFallidos() - 1,
                    usuario.getIntentosFallidos(),
                    "Contraseña incorrecta"
            );

            // Bloquear si supera intentos
            if (usuario.getIntentosFallidos() >= intentosMaximos) {

                LocalDateTime bloqueo = LocalDateTime.now().plusMinutes(tiempoBloqueoMinutos);

                usuario.setBloqueadoHasta(bloqueo);

                auditoriaService.registrarAuditoria(
                        "usuario",
                        usuario.getId().intValue(),
                        "LOGIN_BLOQUEADO",
                        null,
                        bloqueo,
                        "Se alcanzó el máximo de intentos fallidos"
                );
            }

            usuarioRepository.save(usuario);

            throw new RuntimeException("Credenciales inválidas");
        }

        // --- LOGIN CORRECTO ---
        usuario.setIntentosFallidos(0);
        usuario.setBloqueadoHasta(null);

        auditoriaService.registrarAuditoria(
                "usuario",
                usuario.getId().intValue(),
                "LOGIN_OK",
                null,
                null,
                "Inicio de sesión exitoso"
        );

        usuarioRepository.save(usuario);
    }
}

