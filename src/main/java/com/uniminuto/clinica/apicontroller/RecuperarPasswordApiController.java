package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.api.RecuperarPasswordApi;
import com.uniminuto.clinica.model.RecuperarPasswordRq;
import com.uniminuto.clinica.model.RespuestaRs;
import com.uniminuto.clinica.service.UsuarioService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

@RestController
public class RecuperarPasswordApiController implements RecuperarPasswordApi {

    @Autowired
    private UsuarioService usuarioService;

        @org.springframework.web.bind.annotation.PostMapping("/recuperar-contrasena")
    @Override
    public ResponseEntity<RespuestaRs> recuperarPassword(@org.springframework.web.bind.annotation.RequestBody RecuperarPasswordRq request) throws BadRequestException {
        try {
            RespuestaRs respuesta = usuarioService.recuperarPassword(request.getUsername());
            return ResponseEntity.ok(respuesta);
        } catch (MessagingException e) {
            RespuestaRs error = new RespuestaRs();
            error.setStatus(500);
            error.setMensaje("Error al enviar el correo: " + e.getMessage());
            return ResponseEntity.status(500).body(error);
        }
    }
}
