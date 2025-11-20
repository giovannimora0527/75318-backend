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
    
    /**
     * Registra un intento fallido de inicio de sesión en la auditoría.
     * 
     * @param username Nombre de usuario que intentó iniciar sesión
     * @param motivo Motivo del fallo (usuario no existe, contraseña incorrecta, usuario bloqueado, etc.)
     * @param intentosFallidos Número de intentos fallidos consecutivos
     * @param request Request HTTP para obtener la IP de origen
     */
    void registrarIntentoFallidoLogin(String username, String motivo, Integer intentosFallidos, HttpServletRequest request);
    
    /**
     * Registra un bloqueo de usuario por intentos fallidos excesivos.
     * 
     * @param username Nombre de usuario bloqueado
     * @param tiempoBloqueoMinutos Tiempo de bloqueo en minutos
     * @param request Request HTTP para obtener la IP de origen
     */
    void registrarBloqueoUsuario(String username, Integer tiempoBloqueoMinutos, HttpServletRequest request);
    
    /**
     * Registra la creación de una entidad en el sistema.
     * 
     * @param username Nombre de usuario que realizó la operación
     * @param tipoEntidad Tipo de entidad (Usuario, Medico, Paciente, Cita, Receta, Medicamento, Especializacion)
     * @param descripcion Descripción de la operación (ej: "Se creó el usuario admin2")
     * @param request Request HTTP para obtener la IP de origen
     */
    void registrarCrear(String username, String tipoEntidad, String descripcion, HttpServletRequest request);
    
    /**
     * Registra la actualización de una entidad en el sistema.
     * 
     * @param username Nombre de usuario que realizó la operación
     * @param tipoEntidad Tipo de entidad (Usuario, Medico, Paciente, Cita, Receta, Medicamento, Especializacion)
     * @param descripcion Descripción de la operación (ej: "Se actualizó el médico con ID 1")
     * @param request Request HTTP para obtener la IP de origen
     */
    void registrarActualizar(String username, String tipoEntidad, String descripcion, HttpServletRequest request);
    
    /**
     * Registra la eliminación de una entidad en el sistema.
     * 
     * @param username Nombre de usuario que realizó la operación
     * @param tipoEntidad Tipo de entidad (Usuario, Medico, Paciente, Cita, Receta, Medicamento, Especializacion)
     * @param descripcion Descripción de la operación (ej: "Se eliminó la receta con ID 5")
     * @param request Request HTTP para obtener la IP de origen
     */
    void registrarEliminar(String username, String tipoEntidad, String descripcion, HttpServletRequest request);
}

