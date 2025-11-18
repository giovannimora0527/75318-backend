package com.uniminuto.clinica.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.jsonwebtoken.ExpiredJwtException;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.Collections;

@Component
public class JwtTokenFilter extends GenericFilterBean {

    /**
     * Log.
     */
    private static final Logger LOG = Logger.getLogger(JwtTokenFilter.class.getName());

    /**
     * Filtro JWT.
     * @param req Request.
     * @param res Response.
     * @param filterChain Filter chain.
     * @throws IOException IOException.
     * @throws ServletException ServletException.
     */
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        String authHeader = request.getHeader("Authorization");
        String requestURI = request.getRequestURI();
        
        LOG.info(String.format("%s request to %s", request.getMethod(), request.getRequestURL().toString()));
        if (authHeader != null) {
            if (authHeader.trim().isEmpty()) {
                LOG.warning("Authorization header presente pero VACÍO");
            } else {
                LOG.info("Authorization header presente: " + (authHeader.length() > 30 ? authHeader.substring(0, 30) + "..." : authHeader));
            }
        } else {
            LOG.info("No hay Authorization header en la petición");
        }
        
        // Log de todos los headers para debugging
        java.util.Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            if (headerName.toLowerCase().contains("auth") || headerName.toLowerCase().contains("authorization")) {
                LOG.info("Header encontrado: " + headerName + " = " + request.getHeader(headerName));
            }
        }
        
        // Permitir acceso sin token a rutas públicas
        if (requestURI != null && (
                requestURI.contains("/auth/recuperar-contrasena")
                || requestURI.contains("/auth/login")
                || requestURI.contains("/swagger-ui")
                || requestURI.contains("/v3/api-docs")
                || requestURI.contains("/swagger-resources")
                || requestURI.contains("/webjars")
                || requestURI.contains("/documentacion")
                || requestURI.contains("/diagramas")
        )) {
            try {
                filterChain.doFilter(req, res);
            } catch (BadRequestException e) {
                LOG.log(Level.WARNING, "Error en la petición: {0}", e.getMessage());
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("{\"error\": \"Bad Request\","
                        + " \"message\": \"" + e.getMessage() + "\"}");
                return;
            }
            LOG.info("Petición a ruta pública, se permite sin token: " + requestURI);
            return;
        } else if (authHeader != null) {
            // Manejar diferentes formatos de token
            String token = null;
            if (authHeader.startsWith("Bearer ")) {
                token = authHeader.substring(7).trim();
            } else if (authHeader.startsWith("bearer ")) {
                token = authHeader.substring(7).trim();
            } else if (authHeader.startsWith("Bearer")) {
                token = authHeader.substring(6).trim();
            } else {
                // Intentar usar el header completo como token (por si Swagger no agrega Bearer)
                token = authHeader.trim();
            }
            
            if (token != null && !token.isEmpty()) {
                LOG.info("Token extraído (primeros 20 caracteres): " + (token.length() > 20 ? token.substring(0, 20) + "..." : token));
                try {
                    if (isTokenExpired(token)) {
                        LOG.warning("Token expirado");
                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        response.setContentType("application/json");
                        response.getWriter().write("{\"error\":\"Token expirado. Por favor, inicie sesión nuevamente.\"}");
                        response.flushBuffer();
                        return;
                    }
                    // Si el token es válido, establecer autenticación en el contexto de Spring
                    DecodedJWT jwt = JWT.decode(token);
                    String username = jwt.getSubject();
                    LOG.info("Token válido para usuario: " + username);
                    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            username, null, Collections.singletonList(new SimpleGrantedAuthority("USER"))
                        );
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                        LOG.info("Autenticación establecida en el contexto de seguridad");
                    }
                } catch (ExpiredJwtException eje) {
                    LOG.log(Level.WARNING, "Token expirado: {0}", eje.getMessage());
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.setContentType("application/json");
                    response.getWriter().write("{\"error\":\"Token expirado. Por favor, inicie sesión nuevamente.\"}");
                    response.flushBuffer();
                    return;
                } catch (Exception e) {
                    LOG.log(Level.WARNING, "Error al procesar token: {0}", e.getMessage());
                    e.printStackTrace();
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.setContentType("application/json");
                    response.getWriter().write("{\"error\":\"Token inválido: " + e.getMessage() + "\"}");
                    response.flushBuffer();
                    return;
                }
            } else {
                LOG.warning("Token vacío después de extraer del header");
            }
        } else {
            // No hay token en el header - Spring Security manejará la autorización
            // Si el endpoint requiere autenticación, Spring Security rechazará la petición
            LOG.info("Petición sin token a: " + requestURI);
        }
        filterChain.doFilter(req, res);
    }

    /**
     * Valida si el token JWT está expirado.
     * @param token JWT.
     * @return true si está expirado, false si es válido.
     */
    private boolean isTokenExpired(String token) {
        DecodedJWT jwt = JWT.decode(token);
        // Extrae el claim personalizado "fecha_fin_sesion" como Date
        java.util.Date fechaFinSesion = jwt.getClaim("fecha_fin_sesion").asDate();
        if (fechaFinSesion == null) {
            return false; // Si no tiene expiración, se considera válido
        }
        return fechaFinSesion.before(new java.util.Date());
    }
}
