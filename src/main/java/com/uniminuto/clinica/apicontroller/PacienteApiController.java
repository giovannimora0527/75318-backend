package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.api.PacienteApi;
import com.uniminuto.clinica.entity.Paciente;
import com.uniminuto.clinica.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/paciente") // Debe ser igual al de la interfaz
public class PacienteApiController implements PacienteApi {

    @Autowired
    private PacienteService pacienteService;

    // /paciente/listar
    @Override
    public ResponseEntity<List<Paciente>> listarPacientes() {
        return ResponseEntity.ok(pacienteService.listarPacientes());
    }

    // /paciente/buscar?numeroDocumento=xxxx
    @Override
    public ResponseEntity<Paciente> buscarPorNumeroDocumento(@RequestParam String numeroDocumento) {
        return ResponseEntity.ok(
                pacienteService.encontrarPorNumeroDocumento(numeroDocumento)
        );
    }

    // /paciente/fechaNacimiento
    @Override
    public ResponseEntity<List<Paciente>> listarPacientesPorFechaNacimientoDesc() {
        return ResponseEntity.ok(
                pacienteService.listarPacientesPorFechaNacimientoDesc()
        );
    }
}
