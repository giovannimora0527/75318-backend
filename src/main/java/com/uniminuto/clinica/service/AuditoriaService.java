package com.uniminuto.clinica.service;

import com.uniminuto.clinica.entity.AuditoriaSeguridad;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Servicio para gestionar auditoría de seguridad.
 */
public interface AuditoriaService {

    /**
     * Registra un evento de auditoría.
     *
     * @param username          Nombre de usuario ingresado
     * @param motivo            Motivo del registro
     * @param exitoso           Si la operación fue exitosa
     * @param descripcionError  Descripción del error (null si exitoso)
     * @param ipAddress         Dirección IP del cliente
     * @param userAgent         User-Agent del navegador
     */
    void registrarEvento(
            String username,
            String motivo,
            Boolean exitoso,
            String descripcionError,
            String ipAddress,
            String userAgent
    );

    /**
     * Verifica si un usuario tiene intentos sospechosos recientes.
     *
     * @param username Nombre de usuario
     * @return true si hay actividad sospechosa
     */
    boolean tieneIntentosSospechosos(String username);

    /**
     * Obtiene el historial de intentos de recuperación de un usuario.
     *
     * @param username Nombre de usuario
     * @return Lista de registros de auditoría
     */
    List<AuditoriaSeguridad> obtenerHistorialRecuperacion(String username);

    /**
     * Cuenta intentos fallidos recientes de un usuario.
     *
     * @param username Nombre de usuario
     * @param horas    Número de horas a considerar
     * @return Cantidad de intentos fallidos
     */
    Long contarIntentosFallidosRecientes(String username, int horas);
}
