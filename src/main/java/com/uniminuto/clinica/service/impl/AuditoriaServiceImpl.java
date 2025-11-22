package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.entity.AuditoriaSeguridad;
import com.uniminuto.clinica.repository.AuditoriaRepository;
import com.uniminuto.clinica.service.AuditoriaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Implementación del servicio de auditoría de seguridad.
 */
@Slf4j
@Service
public class AuditoriaServiceImpl implements AuditoriaService {

    @Autowired
    private AuditoriaRepository auditoriaRepository;

    // Constantes para motivos de auditoría
    public static final String MOTIVO_RECUPERAR_CONTRASENA = "RECUPERAR_CONTRASENA";
    public static final String MOTIVO_LOGIN_FALLIDO = "LOGIN_FALLIDO";
    public static final String MOTIVO_LOGIN_EXITOSO = "LOGIN_EXITOSO";

    // Límites de seguridad
    private static final int MAX_INTENTOS_24H = 5; // Máximo 5 intentos en 24 horas
    private static final int HORAS_VENTANA = 24;

    @Override
    @Transactional
    public void registrarEvento(
            String username,
            String motivo,
            Boolean exitoso,
            String descripcionError,
            String ipAddress,
            String userAgent) {

        try {
            AuditoriaSeguridad auditoria = AuditoriaSeguridad.builder()
                    .usernameIngresado(username)
                    .motivo(motivo)
                    .exitoso(exitoso)
                    .descripcionError(descripcionError)
                    .ipAddress(ipAddress)
                    .userAgent(userAgent)
                    .fechaHora(LocalDateTime.now())
                    .build();

            auditoriaRepository.save(auditoria);

            // Log en consola para monitoreo
            if (exitoso) {
                log.info("Auditoría registrada [EXITOSO] - Username: {}, Motivo: {}, IP: {}",
                        username, motivo, ipAddress);
            } else {
                log.warn("Auditoría registrada [FALLIDO] - Username: {}, Motivo: {}, Error: {}, IP: {}",
                        username, motivo, descripcionError, ipAddress);
            }

            // Verificar si hay intentos sospechosos
            if (tieneIntentosSospechosos(username)) {
                log.error("⚠️ ALERTA DE SEGURIDAD: Usuario '{}' tiene múltiples intentos de {}. IP: {}",
                        username, motivo, ipAddress);
            }

        } catch (Exception e) {
            log.error("Error al registrar auditoría para usuario: {}", username, e);
            // No lanzamos excepción para no afectar el flujo principal
        }
    }

    @Override
    public boolean tieneIntentosSospechosos(String username) {
        LocalDateTime fechaLimite = LocalDateTime.now().minusHours(HORAS_VENTANA);
        Long intentosFallidos = auditoriaRepository.contarIntentosFallidos(
                username,
                MOTIVO_RECUPERAR_CONTRASENA,
                fechaLimite
        );
        return intentosFallidos >= MAX_INTENTOS_24H;
    }

    @Override
    public List<AuditoriaSeguridad> obtenerHistorialRecuperacion(String username) {
        LocalDateTime fechaLimite = LocalDateTime.now().minusDays(7); // Últimos 7 días
        return auditoriaRepository.findRecuperacionesRecientes(username, fechaLimite);
    }

    @Override
    public Long contarIntentosFallidosRecientes(String username, int horas) {
        LocalDateTime fechaLimite = LocalDateTime.now().minusHours(horas);
        return auditoriaRepository.contarIntentosFallidos(
                username,
                MOTIVO_RECUPERAR_CONTRASENA,
                fechaLimite
        );
    }
}
