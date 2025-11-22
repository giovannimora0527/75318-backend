package com.uniminuto.clinica.security;


import javax.servlet.http.HttpServletRequest;

import com.uniminuto.clinica.utils.JwtUtil;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;

public class RoleChecker {

    /**
     * Verifica el rol del usuario según el token JWT.
     * Si roles está vacío → todos pueden acceder.
     * Si roles tiene elementos → solo esos roles pueden acceder.
     */
    public static void checkRole(String... roles) {
        // Obtener token del header Authorization
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = request.getHeader("Authorization");

        if (token == null || token.isEmpty()) {
            throw new RuntimeException("No se encontró el token de autenticación");
        }

        String userRole = JwtUtil.getRoleFromToken(token);

        // Si no se especifican roles, todos pueden ver
        if (roles == null || roles.length == 0) {
            return;
        }

        boolean autorizado = Arrays.asList(roles).contains(userRole);

        if (!autorizado) {
            throw new RuntimeException("No autorizado para este recurso");
        }
    }
}


