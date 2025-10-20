/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.api.PacienteApi;
import com.uniminuto.clinica.model.PacienteRq;
import com.uniminuto.clinica.model.PacienteRs;
import com.uniminuto.clinica.model.RespuestaRs;
import com.uniminuto.clinica.service.PacienteService;
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
public class PacienteApiController implements PacienteApi {

    @Autowired
    private PacienteService pacienteService;

    @Override
    public List<PacienteRs> listarPacientes() {
        return pacienteService.listarPacientes();
    }

    @Override
    public List<PacienteRs> listarPacientesRecientes() {
        return pacienteService.listarPacientesRecientes();
    }

    @Override
    public ResponseEntity<RespuestaRs> guardarPaciente(PacienteRq pacienteRq) throws BadRequestException {
        RespuestaRs respuesta = pacienteService.guardarPaciente(pacienteRq);
        return ResponseEntity.ok(respuesta);
    }
    @Override
    public ResponseEntity<RespuestaRs> actualizarPaciente(PacienteRq pacienteRq) throws BadRequestException {
        RespuestaRs respuesta = pacienteService.guardarPaciente(pacienteRq);
        return ResponseEntity.ok(respuesta);
}
}
