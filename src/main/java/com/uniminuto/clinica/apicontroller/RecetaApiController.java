/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.api.RecetaApi;
import com.uniminuto.clinica.model.RecetaRq;
import com.uniminuto.clinica.model.RecetaRs;
import com.uniminuto.clinica.model.RespuestaRs;
import com.uniminuto.clinica.service.RecetaService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 
 * @author Usuario
 */

@RestController
public class RecetaApiController implements RecetaApi {

    @Autowired
    private RecetaService recetaService;

    @Override
    public List<RecetaRs> listarRecetas() {
        return recetaService.listarRecetas();
    }

    @Override
    public List<RecetaRs> listarRecetasRecientes() {
        return recetaService.listarRecetasRecientes();
    }

    @Override
    public ResponseEntity<RespuestaRs> guardarReceta(RecetaRq recetaRq) throws BadRequestException {
        RespuestaRs respuesta = recetaService.guardarReceta(recetaRq);
        return ResponseEntity.ok(respuesta);
    }
    
    @Override
    public ResponseEntity<RespuestaRs> actualizarReceta(RecetaRq recetaRq) throws BadRequestException {
        RespuestaRs respuesta = recetaService.guardarReceta(recetaRq);
        return ResponseEntity.ok(respuesta);
    }
    
    @Override
    public List<RecetaRs> listarRecetasPorCita(Long citaId) {
        return recetaService.listarRecetasPorCita(citaId);
    }
    
    @Override
    public List<RecetaRs> listarRecetasPorMedicamento(Long medicamentoId) {
        return recetaService.listarRecetasPorMedicamento(medicamentoId);
    }
}