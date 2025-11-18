package com.uniminuto.clinica.service;

import javax.servlet.http.HttpServletRequest;

/**
 * Servicio para registrar eventos de auditoría en el sistema.
 * 
 * @author Sistema
 */
public interface AuditoriaService {
    
    /**
     * Registra un evento de auditoría de recuperación de contraseña exitosa.
     * 
     * @param username Nombre de usuario que solicitó la recuperación
     * @param request Request HTTP para obtener la IP de origen
     */
    void registrarRecuperacionExito(String username, HttpServletRequest request);
    
    /**
     * Registra un evento de auditoría de recuperación de contraseña fallida.
     * 
     * @param username Nombre de usuario que intentó la recuperación (puede ser inválido)
     * @param descripcion Descripción del error
     * @param request Request HTTP para obtener la IP de origen
     */
    void registrarRecuperacionError(String username, String descripcion, HttpServletRequest request);
}

