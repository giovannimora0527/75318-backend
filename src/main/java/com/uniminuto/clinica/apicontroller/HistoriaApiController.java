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

@RestController
public class HistoriaApiController implements HistoriaApi {

    @Autowired
    private HistoriaService historiaService;

    @Override
    public ResponseEntity<List<Historia>> listarHistorias() {
        return ResponseEntity.ok(this.historiaService.listarTodasLasHistorias());
    }

    @Override
    public ResponseEntity<RespuestaRs> guardarHistoria(HistoriaRq historiaNuevo)
            throws BadRequestException {
        return ResponseEntity.ok(this.historiaService.guardarHistoria(historiaNuevo));
    }

    @Override
    public ResponseEntity<RespuestaRs> actualizarHistoria(HistoriaRq historia)
            throws BadRequestException {
        return ResponseEntity.ok(this.historiaService.actualizarHistoria(historia));
    }
}
