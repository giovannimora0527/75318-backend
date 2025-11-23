package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.api.RecuperacionPasswordApi;
import com.uniminuto.clinica.model.RecuperacionPasswordRequest;
import com.uniminuto.clinica.model.RecuperacionPasswordResponse;
import com.uniminuto.clinica.service.RecuperacionPasswordService;
import javax.servlet.http.HttpServletRequest;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RecuperacionPasswordApiController implements RecuperacionPasswordApi {
    
    @Autowired
    private RecuperacionPasswordService servicio;
    
    @Autowired
    private HttpServletRequest httpRequest;
    
    @Override
    public ResponseEntity<RecuperacionPasswordResponse> recuperarPassword(
            RecuperacionPasswordRequest request) throws BadRequestException {
        
        String ip = obtenerDireccionIP();
        
        RecuperacionPasswordResponse response = servicio.solicitarRecuperacion(
            request.getUsername(), 
            ip
        );
        
        return ResponseEntity.ok(response);
    }
    
    private String obtenerDireccionIP() {
        String ip = httpRequest.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = httpRequest.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = httpRequest.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = httpRequest.getRemoteAddr();
        }
        return ip;
    }
}