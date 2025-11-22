package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.api.HistoriaApi;
import com.uniminuto.clinica.entity.Historia;
import com.uniminuto.clinica.model.HistoriaRq;
import com.uniminuto.clinica.model.RespuestaRs;
import com.uniminuto.clinica.service.HistoriaService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

import static com.uniminuto.clinica.security.RoleChecker.checkRole;

@RestController
public class HistoriaApiController implements HistoriaApi {

    @Autowired
    private HistoriaService historiaService;

    @Override
    public ResponseEntity<List<Historia>> listarHistorias() {
        checkRole();
        return ResponseEntity.ok(this.historiaService.listarTodasLasHistorias());
    }

    @Override
    public ResponseEntity<RespuestaRs> guardarHistoria(HistoriaRq historiaNuevo)
            throws BadRequestException {
        checkRole("MEDICO","ADMINISTRADOR");
        return ResponseEntity.ok(this.historiaService.guardarHistoria(historiaNuevo));
    }

    @Override
    public ResponseEntity<RespuestaRs> actualizarHistoria(HistoriaRq historia)
            throws BadRequestException {
        checkRole("MEDICO","ADMINISTRADOR");
        return ResponseEntity.ok(this.historiaService.actualizarHistoria(historia));
    }

    @Override
    public ResponseEntity<RespuestaRs> eliminarHistoria(Integer idHistoria) throws BadRequestException {
        checkRole("MEDICO","ADMINISTRADOR");
        return ResponseEntity.ok(this.historiaService.eliminarHistoria(idHistoria));
    }
}
