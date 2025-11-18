package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.api.AutenticarApi;
import com.uniminuto.clinica.model.AutenticatorRs;
import com.uniminuto.clinica.model.AuthenticatorRq;
import com.uniminuto.clinica.model.RespuestaRs;
import com.uniminuto.clinica.service.AutenticarService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
public class AutenticarApiController implements AutenticarApi {

    @Autowired
    private AutenticarService autenticarService;

    @Override
    public ResponseEntity<AutenticatorRs> autenticar(AuthenticatorRq request) throws BadRequestException {
        // Obtener HttpServletRequest del contexto de Spring
        HttpServletRequest servletRequest = ((org.springframework.web.context.request.ServletRequestAttributes) 
                org.springframework.web.context.request.RequestContextHolder.currentRequestAttributes())
                .getRequest();
        return ResponseEntity.ok(this.autenticarService.autenticar(request, servletRequest));
    }

    @Override
    public ResponseEntity<RespuestaRs> recuperarContrasena(Map<String, String> request) throws BadRequestException {
        String username = request.get("username");
        if (username == null || username.isEmpty()) {
            throw new BadRequestException("El username es requerido");
        }
        // Obtener HttpServletRequest del contexto de Spring
        HttpServletRequest servletRequest = ((org.springframework.web.context.request.ServletRequestAttributes) 
                org.springframework.web.context.request.RequestContextHolder.currentRequestAttributes())
                .getRequest();
        return ResponseEntity.ok(this.autenticarService.recuperarContrasena(username, servletRequest));
    }
}
