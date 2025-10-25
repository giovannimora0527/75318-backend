package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.api.MedicamentoApi;
import com.uniminuto.clinica.model.MedicamentoRq;
import com.uniminuto.clinica.model.MedicamentoRs;
import com.uniminuto.clinica.model.RespuestaRs;
import com.uniminuto.clinica.service.MedicamentoService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MedicamentoApiController implements MedicamentoApi {

    @Autowired
    private MedicamentoService medicamentoService;

    @Override
    public List<MedicamentoRs> listarMedicamentos() {
        return medicamentoService.listarMedicamentos();
    }

    @Override
    public List<MedicamentoRs> listarMedicamentosRecientes() {
        return medicamentoService.listarMedicamentosRecientes();
    }

    @Override
    public ResponseEntity<RespuestaRs> guardarMedicamento(MedicamentoRq medicamentoRq) throws BadRequestException {
        RespuestaRs respuesta = medicamentoService.guardarMedicamento(medicamentoRq);
        return ResponseEntity.ok(respuesta);
    }
    
    @Override
    public ResponseEntity<RespuestaRs> actualizarMedicamento(MedicamentoRq medicamentoRq) throws BadRequestException {
        RespuestaRs respuesta = medicamentoService.guardarMedicamento(medicamentoRq);
        return ResponseEntity.ok(respuesta);
    }
}