package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.api.RecetaApi;
import com.uniminuto.clinica.entity.Receta;
import com.uniminuto.clinica.model.RecetaRq;
import com.uniminuto.clinica.model.RespuestaRs;
import com.uniminuto.clinica.service.RecetaService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RecetaApiController implements RecetaApi{
    @Autowired
    private RecetaService recetaService;

    @Override
    public ResponseEntity<List<Receta>> listarTodasRecetas() {
        return ResponseEntity.ok(recetaService.listarTodasRecetas());
    }

    @Override
    public ResponseEntity<List<Receta>> listarRecetasDesc() {
        return ResponseEntity.ok(recetaService.listarRecetasDesc());
    }

    @Override
    public ResponseEntity<List<Receta>> listarRecetasPorCita(Long citaId)
            throws BadRequestException {
        return ResponseEntity.ok(recetaService.buscarPorCita(citaId));
    }

    @Override
    public ResponseEntity<List<Receta>> listarRecetasPorMedicamento(Integer medicamentoId) {
        return ResponseEntity.ok(recetaService.buscarPorMedicamento(medicamentoId));
    }

    @Override
    public ResponseEntity<Receta> buscarRecetaPorId(Long id) throws BadRequestException {
        return ResponseEntity.ok(recetaService.buscarRecetaPorId(id));
    }

    @Override
    public ResponseEntity<RespuestaRs> guardarReceta(RecetaRq recetaRq)
            throws BadRequestException {
        return ResponseEntity.ok(recetaService.guardarReceta(recetaRq));
    }
}
