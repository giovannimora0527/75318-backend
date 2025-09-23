/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.api.CitaApi;
import com.uniminuto.clinica.model.CitaRq;
import com.uniminuto.clinica.model.CitaRs;
import com.uniminuto.clinica.model.RespuestaRs;
import com.uniminuto.clinica.service.CitaService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CitaApiController implements CitaApi {

    @Autowired
    private CitaService citaService;

    @Override
    public ResponseEntity<RespuestaRs> guardarCita(CitaRq citaRq) throws BadRequestException {
        return ResponseEntity.ok(citaService.guardarCita(citaRq));
    }

    @Override
    public List<CitaRs> listarCitas() {
        return citaService.listarCitas();
    }

    @Override
    public List<CitaRs> listarCitasRecientes() {
        return citaService.listarCitasRecientes();
    }
}
