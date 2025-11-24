package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.api.AutenticarApi;
import com.uniminuto.clinica.model.AutenticatorRs;
import com.uniminuto.clinica.model.AuthenticatorRq;
import com.uniminuto.clinica.model.RecuperarPasswordRq;
import com.uniminuto.clinica.model.RespuestaRs;
import com.uniminuto.clinica.service.AutenticarService;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
public class AutenticarApiController implements AutenticarApi {

    @Autowired
    private AutenticarService autenticarService;


    @Override
    public ResponseEntity<AutenticatorRs> autenticar(AuthenticatorRq request, HttpServletRequest httpRequest) throws BadRequestException {
        try {
            String ipAddress = obtenerIpCliente(httpRequest); // ← IP normalizada
            String userAgent = httpRequest.getHeader("User-Agent");

            // Llamar al servicio con IP y User-Agent
            AutenticatorRs response = autenticarService.autenticar(request, ipAddress, userAgent);
            return ResponseEntity.ok(response);
        } catch (BadRequestException e) {
            log.error("Error en autenticación desde IP {}: {}", obtenerIpCliente(httpRequest), e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @Override
    public ResponseEntity<RespuestaRs> recuperarPassword(RecuperarPasswordRq request, HttpServletRequest httpRequest) {
        try {
            String ipAddress = obtenerIpCliente(httpRequest);
            String userAgent = httpRequest.getHeader("User-Agent");

            log.info("Solicitud de recuperación de contraseña desde IP: {}", ipAddress);

            RespuestaRs response = autenticarService.recuperarPassword(request, ipAddress, userAgent);
            return ResponseEntity.ok(response);

        } catch (MessagingException e) {
            log.error("Error al enviar correo de recuperación", e);
            return crearRespuestaExitoGenerica();

        } catch (Exception e) {
            log.error("Error inesperado en recuperación de contraseña", e);
            return crearRespuestaExitoGenerica();
        }
    }

    /**
     * Crea la respuesta genérica de éxito para recuperación de contraseña.
     */
    private ResponseEntity<RespuestaRs> crearRespuestaExitoGenerica() {
        RespuestaRs response = new RespuestaRs();
        response.setStatus(200);
        response.setMensaje("Si el usuario existe, se ha enviado un correo electrónico " +
                "con instrucciones para recuperar la contraseña.");
        return ResponseEntity.ok(response);
    }

    /**
     * Convierte IPs IPv6 de loopback a su equivalente IPv4.
     *
     * @param ip Dirección IP original
     * @return IP normalizada (127.0.0.1 para localhost)
     */
    private String normalizarIp(String ip) {
        if (ip == null || ip.isEmpty()) {
            return "0.0.0.0";
        }

        // Caso IPv6 de loopback
        if ("0:0:0:0:0:0:0:1".equals(ip) || "::1".equals(ip)) {
            return "127.0.0.1";
        }

        // Caso IPv4 ya normal
        if (ip.matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}")) {
            return ip;
        }

        // Para otros casos (IPv6 públicas, etc.), devolver tal cual
        return ip;
    }

    /**
     * Obtiene la dirección IP real del cliente, considerando proxies y balanceadores.
     */
    private String obtenerIpCliente(HttpServletRequest request) {
        String[] headers = {
                "X-Forwarded-For",
                "Proxy-Client-IP",
                "WL-Proxy-Client-IP",
                "HTTP_X_FORWARDED_FOR",
                "HTTP_X_FORWARDED",
                "HTTP_X_CLUSTER_CLIENT_IP",
                "HTTP_CLIENT_IP",
                "HTTP_FORWARDED_FOR",
                "HTTP_FORWARDED",
                "HTTP_VIA"
        };

        for (String header : headers) {
            String ipAddress = request.getHeader(header);
            if (ipAddress != null && !ipAddress.isEmpty() && !"unknown".equalsIgnoreCase(ipAddress)) {
                // Tomar la primera IP si hay varias
                if (ipAddress.contains(",")) {
                    ipAddress = ipAddress.split(",")[0].trim();
                }
                return normalizarIp(ipAddress);
            }
        }

        return normalizarIp(request.getRemoteAddr());
    }
}
