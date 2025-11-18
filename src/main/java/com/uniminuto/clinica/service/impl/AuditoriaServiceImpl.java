package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.entity.Auditoria;
import com.uniminuto.clinica.repository.AuditoriaRepository;
import com.uniminuto.clinica.service.AuditoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * Implementación del servicio de auditoría.
 * 
 * @author Sistema
 */
@Service
public class AuditoriaServiceImpl implements AuditoriaService {
    
    @Autowired
    private AuditoriaRepository auditoriaRepository;
    
    @Override
    public void registrarRecuperacionExito(String username, HttpServletRequest request) {
        Auditoria auditoria = new Auditoria();
        auditoria.setFechaHora(LocalDateTime.now());
        auditoria.setNombreUsuario(username);
        auditoria.setDescripcion("Recuperación de contraseña exitosa. Se envió contraseña temporal al correo del usuario.");
        auditoria.setTipoEvento("RECUPERACION_CONTRASENA_EXITO");
        auditoria.setIpOrigen(obtenerIpOrigen(request));
        
        auditoriaRepository.save(auditoria);
    }
    
    @Override
    public void registrarRecuperacionError(String username, String descripcion, HttpServletRequest request) {
        Auditoria auditoria = new Auditoria();
        auditoria.setFechaHora(LocalDateTime.now());
        auditoria.setNombreUsuario(username);
        auditoria.setDescripcion(descripcion);
        auditoria.setTipoEvento("RECUPERACION_CONTRASENA_ERROR");
        auditoria.setIpOrigen(obtenerIpOrigen(request));
        
        auditoriaRepository.save(auditoria);
    }
    
    @Override
    public void registrarIntentoFallidoLogin(String username, String motivo, Integer intentosFallidos, HttpServletRequest request) {
        Auditoria auditoria = new Auditoria();
        auditoria.setFechaHora(LocalDateTime.now());
        auditoria.setNombreUsuario(username != null ? username : "DESCONOCIDO");
        auditoria.setDescripcion(String.format("Intento fallido de inicio de sesión. Motivo: %s. Intentos fallidos consecutivos: %d", motivo, intentosFallidos));
        auditoria.setTipoEvento("LOGIN_FALLIDO");
        auditoria.setIpOrigen(obtenerIpOrigen(request));
        
        auditoriaRepository.save(auditoria);
    }
    
    @Override
    public void registrarBloqueoUsuario(String username, Integer tiempoBloqueoMinutos, HttpServletRequest request) {
        Auditoria auditoria = new Auditoria();
        auditoria.setFechaHora(LocalDateTime.now());
        auditoria.setNombreUsuario(username);
        auditoria.setDescripcion(String.format("Usuario bloqueado automáticamente por %d intentos fallidos consecutivos. Bloqueo temporal de %d minutos.", 3, tiempoBloqueoMinutos));
        auditoria.setTipoEvento("BLOQUEO_USUARIO");
        auditoria.setIpOrigen(obtenerIpOrigen(request));
        
        auditoriaRepository.save(auditoria);
    }
    
    /**
     * Obtiene la IP de origen del request.
     * 
     * @param request Request HTTP
     * @return IP de origen
     */
    private String obtenerIpOrigen(HttpServletRequest request) {
        if (request == null) {
            return "DESCONOCIDA";
        }
        
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        
        return ip != null ? ip : "DESCONOCIDA";
    }
}

