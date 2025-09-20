package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.api.PacienteApi;
import com.uniminuto.clinica.entity.Paciente;
import com.uniminuto.clinica.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PacienteApiController implements PacienteApi {

    @Autowired
    private PacienteService pacienteService;

    @GetMapping
    public ResponseEntity<List<Paciente>> listarPacientes() {
        return ResponseEntity.ok(pacienteService.listarPacientes());
    }

    @GetMapping("/{numeroDocumento}")
    public ResponseEntity<Paciente> findByNumeroDocumento(@PathVariable String numeroDocumento) {
        return ResponseEntity.ok(pacienteService.encontrarPorNumeroDocumento(numeroDocumento));
    }

    
    public ResponseEntity<List<Paciente>> listarPacientesPorFechaNacimientoAsc() {
        return ResponseEntity.ok(pacienteService.listarPacientesPorFechaNacimientoDesc());
    }

    @Override
    public ResponseEntity<Paciente> buscarPorNumeroDocumento(String numeroDocumento) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ResponseEntity<List<Paciente>> listarPacientesPorFechaNacimientoDesc() {
        return ResponseEntity.ok(pacienteService.listarPacientesPorFechaNacimientoDesc());
    }
}
