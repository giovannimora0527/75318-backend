package com.uniminuto.clinica.audit;

import com.uniminuto.clinica.model.AuditLog;
import com.uniminuto.clinica.service.AuditLogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Aspect
@Component
public class AuditLogAspect {

    @Autowired
    private AuditLogService auditLogService;

    // LOG DE EXITOS

    @AfterReturning("execution(* com.uniminuto.clinica.service..*(..))")
    public void logSuccess(JoinPoint joinPoint) {
        saveLog(joinPoint, "SUCCESS");
    }

    // LOG DE ERRORES

    @AfterThrowing(pointcut = "execution(* com.uniminuto.clinica.service..*(..))", throwing = "ex")
    public void logError(JoinPoint joinPoint, Throwable ex) {
        saveLog(joinPoint, "ERROR: " + ex.getMessage());
    }

    // GUARDAR LOG
    private void saveLog(JoinPoint joinPoint, String status) {
        String methodName = joinPoint.getSignature().getName();

        if (methodName.equals("saveLog")) {
            return;
        }

        AuditLog log = new AuditLog();
        String username = getCurrentUsername();
        log.setUsername(username);
        log.setEventType(methodName);
        log.setTimestamp(LocalDateTime.now());
        log.setDescription(generateDescription(methodName, username, status));

        auditLogService.saveLog(log);
    }

    // GENERAR DESCRIPCION LEGIBLE
    
    private String generateDescription(String methodName, String username, String status) {
        switch (methodName) {
            case "recuperarPassword":
                return "El usuario " + username + " solicitó restablecer la contraseña";
            case "guardarUsuario":
                return "El usuario " + username + " creó un nuevo usuario";
            case "actualizarUsuario":
                return "El usuario " + username + " modificó un usuario";
            case "login":
                return "El usuario " + username + " intentó iniciar sesión (" + status + ")";
            default:
                return "El usuario " + username + " ejecutó " + methodName + " (" + status + ")";
        }
    }
    // OBTENER USUARIO ACTUAL
    private String getCurrentUsername() {
        try {
            return SecurityContextHolder.getContext().getAuthentication().getName();
        } catch (Exception e) {
            return "SYSTEM";
        }
    }
}
