package com.uniminuto.clinica.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Utilidad para obtener información del usuario autenticado.
 * 
 * @author Sistema
 */
public class SecurityUtils {
    
    /**
     * Obtiene el nombre de usuario del contexto de seguridad actual.
     * 
     * @return Nombre de usuario o null si no hay usuario autenticado
     */
    public static String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() != null) {
            // El principal puede ser un String (username) o un objeto UserDetails
            Object principal = authentication.getPrincipal();
            if (principal instanceof String) {
                return (String) principal;
            } else {
                // Si es un objeto UserDetails, obtener el username
                return authentication.getName();
            }
        }
        return null;
    }
}

