package com.uniminuto.clinica.apicontroller;
import com.uniminuto.clinica.api.RecetaApi;
import com.uniminuto.clinica.model.RecetaRq;
import com.uniminuto.clinica.model.RespuestaRs;
import com.uniminuto.clinica.service.RecetaService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RecetaApiController implements RecetaApi {

    @Autowired
    private RecetaService recetaService;

    @Override
    public ResponseEntity<RespuestaRs> crearReceta(RecetaRq recetaRq)
            throws BadRequestException {
        return ResponseEntity.ok(this.recetaService.crearReceta(recetaRq));

    }
}
