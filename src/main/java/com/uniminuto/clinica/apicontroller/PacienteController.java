package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.entity.Paciente;
import com.uniminuto.clinica.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pacientes")
@CrossOrigin(origins = "*")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

   
    @GetMapping
    public ResponseEntity<List<Paciente>> listarPacientes() {
        List<Paciente> pacientes = pacienteService.listarPacientes();
        return ResponseEntity.ok(pacientes);
    }

  
    @GetMapping("/documento/{numeroDocumento}")
