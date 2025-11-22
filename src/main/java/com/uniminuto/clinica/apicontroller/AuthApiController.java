package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.api.AuthApi;
import com.uniminuto.clinica.model.ChangePasswordRq;
import com.uniminuto.clinica.model.RespuestaRs;
import com.uniminuto.clinica.security.JwtUtil;
import com.uniminuto.clinica.service.AuthService;
import com.uniminuto.clinica.service.AutenticarService;

import javax.servlet.http.HttpServletRequest;
import javax.mail.MessagingException;
import java.util.Map;

import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
public class AuthApiController implements AuthApi {

    @Autowired
    private AuthService authService;

    @Autowired
    private AutenticarService autenticarService;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public ResponseEntity<?> recover(Map<String, String> body, HttpServletRequest request) {

        String username = body.get("username");
        String ip = request.getRemoteAddr();

        try {
            String temp = authService.recoverPassword(username, ip);

            if (temp != null) {
                System.out.println("🔐 TEMP PASSWORD (DEBUG) para usuario '" + username + "': " + temp);
            }

        } catch (MessagingException e) {
            System.err.println("❌ Error enviando correo: " + e.getMessage());

            return ResponseEntity.status(500)
                    .body(Map.of("message", "Ocurrió un error enviando el correo de recuperación."));
        }

        return ResponseEntity.ok(
                Map.of("message", "Si el usuario existe, se ha iniciado el proceso de recuperación.")
        );
    }

    @Override
    public ResponseEntity<RespuestaRs> cambiarPassword(ChangePasswordRq request,
                                                       HttpServletRequest httpRequest)
            throws BadRequestException {

        String token = jwtUtil.extractTokenFromRequest(httpRequest);

        if (token == null || !jwtUtil.validateToken(token)) {
            throw new BadRequestException("Token inválido o expirado");
        }

        String username = jwtUtil.extractUsername(token);

        RespuestaRs respuesta = autenticarService.cambiarPassword(username, request);

        return ResponseEntity.ok(respuesta);
    }
}
