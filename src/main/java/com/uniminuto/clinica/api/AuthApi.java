package com.uniminuto.clinica.api;

import com.uniminuto.clinica.model.ChangePasswordRq;
import com.uniminuto.clinica.model.RespuestaRs;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@CrossOrigin(origins = "*")
@RequestMapping("/auth")
public interface AuthApi {

    @RequestMapping(value = "/recuperar-contrasena",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<?> recover(@RequestBody Map<String, String> body,
                              HttpServletRequest request);

    @RequestMapping(value = "/cambiar-password",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<RespuestaRs> cambiarPassword(@RequestBody ChangePasswordRq request,
                                                HttpServletRequest httpRequest)
            throws BadRequestException;
}
