package com.uniminuto.clinica.security;

import com.auth0.jwt.algorithms.Algorithm;
import com.uniminuto.clinica.entity.Usuario;
import com.auth0.jwt.JWT;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.interfaces.DecodedJWT;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long jwtExpiration;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    /**
     * 🔥 Versión extendida: permite agregar claims adicionales
     */
    public String generateToken(Usuario usuario, boolean requirePasswordChange) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey.getBytes());

        return JWT.create()
                .withSubject(usuario.getUsername())
                .withClaim("fecha_inicio_sesion", new Date())
                .withClaim("fecha_fin_sesion", new Date(System.currentTimeMillis() + jwtExpiration))
                .withClaim("correo", usuario.getEmail())
                .withClaim("requirePasswordChange", requirePasswordChange)
                .sign(algorithm);
    }

    /**
     * ⚠️ Versión original preservada para compatibilidad
     */
    public String generateToken(Usuario usuario) {
        return generateToken(usuario, false);
    }

    // Obtener el username del token
    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // Obtener la fecha de expiración del token
    public Date getExpirationDateFromToken(String token) {
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getClaim("fecha_fin_sesion").asDate();
    }

    // Obtener claim personalizado
    public Boolean extractRequirePasswordChange(String token) {
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getClaim("requirePasswordChange").asBoolean();
    }

    // Validar token
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Extraer username directamente desde el request HTTP
     */
    public String extractUsernameFromRequest(javax.servlet.http.HttpServletRequest request) {
        final String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) return null;
        return extractUsername(header.substring(7));
    }

    public String extractTokenFromRequest(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }

}
