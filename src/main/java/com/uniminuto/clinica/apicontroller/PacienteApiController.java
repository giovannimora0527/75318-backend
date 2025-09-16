package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.api.PacienteApi;
import com.uniminuto.clinica.entity.Paciente;
import com.uniminuto.clinica.service.PacienteService;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author crash
 */
@RestController
public class PacienteApiController implements PacienteApi {

    private final PacienteService pacienteService;

    public PacienteApiController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @Override
    public ResponseEntity<List<Paciente>> obtenerPacientes() {
        List<Paciente> pacientes = pacienteService.listarTodo();
        return ResponseEntity.ok(pacientes);
    }

    @Override
    public ResponseEntity<Paciente> buscarPorDocumento(String numeroDocumento) 
            throws BadRequestException {
        Paciente paciente = pacienteService.encontrarPorDocumentoIdentidad(numeroDocumento);
        return ResponseEntity.ok(paciente);
    }
}
