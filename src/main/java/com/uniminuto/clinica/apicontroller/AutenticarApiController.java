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
    public ResponseEntity<AutenticatorRs> autenticar(AuthenticatorRq request) throws BadRequestException {
        return ResponseEntity.ok(this.autenticarService.autenticar(request));
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
                return ipAddress;
            }
        }

        return request.getRemoteAddr();
    }
}

