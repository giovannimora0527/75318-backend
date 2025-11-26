package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.api.AuditoriaApi;
import com.uniminuto.clinica.entity.RecuperarPasswordAuditoria;
import com.uniminuto.clinica.service.RecuperarPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AuditoriaApiController implements AuditoriaApi {

    @Autowired
    private RecuperarPasswordService recuperarPasswordService;

    @Override
    public ResponseEntity<List<RecuperarPasswordAuditoria>> listarTodosLosRegistros() {
        return ResponseEntity.ok(recuperarPasswordService.listarTodosLosRegistros());
    }
}
